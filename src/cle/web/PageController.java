package cle.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cle.dao.*;
import cle.domain.Page;
import cle.domain.Resource;
import cle.filehandler.HTMLSanitiser;
import cle.filehandler.HtmlParser;
import cle.filehandler.ResourceUpload;
import cle.filehandler.YouTube;

@Controller
public class PageController {
	@Autowired(required = true)
	UserDao userDao;
	@Autowired(required = true)
	ImageDao imageDao;
	@Autowired(required = true)
	ModuleDao moduleDao;
	@Autowired(required = true)
	CollectionDao collectionDao;
	@Autowired(required = true)
	ResourceDao resourceDao;
	@Autowired(required = true)
	ResourceUpload resourceUpload;
	@Autowired(required = true)
	PageDao pageDao;

	// For ajax requests for page html
	@RequestMapping(value = "/page/getContent", method = RequestMethod.GET)
	public @ResponseBody
	String getPageContent(@RequestParam int pageId) {

		Page page = pageDao.find(pageId);

		return page.getContent();

	}
	
	@RequestMapping(value = "/page/getPage.json", method = RequestMethod.GET)
	public @ResponseBody
	Page getPageJSON(@RequestParam int pageId) {

		Page page = pageDao.find(pageId);

		return page;

	}
	
	@RequestMapping(value = "/page/id/{pageId}/editDetails", method = RequestMethod.POST)
	public @ResponseBody
	ResponseEntity<String> editDetails(@PathVariable("pageId") int pageId,@RequestParam(value="value") String value, @RequestParam(value="id", required=false) String id) {
		Page page = pageDao.find(pageId);
		if(id.equals("editableTitle")){
			page.setTitle(value);
			
		}else if(id.equals("editableTags")){
			page.setTags(value);
		}
		pageDao.saveOrUpdate(page);
		

		return new ResponseEntity<String>(HttpStatus.OK);

	}

	@RequestMapping(value = "/page/update", method = RequestMethod.POST)
	public ResponseEntity<String> updatePage(HttpServletRequest request,
			@RequestParam("formPageId") int pageId,
			@RequestParam("formPageData") String pageData) {
		Page page = pageDao.find(pageId);
		String sanitisedData = HTMLSanitiser.encodeInvalidMarkup(pageData);
		page.setContent(HtmlParser.addElementIds(sanitisedData));
		pageDao.saveOrUpdate(page);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/page/new", method = RequestMethod.POST)
	public ResponseEntity<String> newPage(HttpServletRequest request,
			@RequestParam("resourceId") int resourceId, @RequestParam(required=false, value="pageType") String pageType, @RequestParam(required=false, value="videoId") String videoId) {
		System.out.println("new page for " + resourceId);

		Resource resource = resourceDao.findResourceAndPages(resourceId);

		int noOfPages = 0;
		noOfPages = resource.getPages().size();
		Page page = new Page(resource, noOfPages + 1); // By default the new
		// numbered as the next one.					// page will be
				
		page.setContent("Empty Page"); 
		page.setTitle("Untitled");
		
		if(pageType != null && pageType.contains("youtube")){
			page.setTitle("YouTube");
			page.setContent(YouTube.generateEmbed(videoId));
			
			
		}
		resource.getPages().add(page);

		String pageId = page.getPageid() + "";
		// resourceDao.saveOrUpdate(resource);
		pageDao.saveOrUpdate(page);

		return new ResponseEntity<String>(pageId, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/page/getPageList", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getPageList(@RequestParam("resourceId") int resourceId) {
		ModelMap map = new ModelMap();
		List<Page> list = pageDao.findPagesOfResource(resourceId);

		map.addAttribute(list);
		return map;

	}
	
	@RequestMapping(value = "/page/delete", method = RequestMethod.POST)
	public ResponseEntity<String> deletePage(HttpServletRequest request,
			@RequestParam("pageId") int pageId) {
		Page page = pageDao.find(pageId);
	
		pageDao.delete(page);

		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
