package cle.web;

import java.util.Calendar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cle.dao.*;
import cle.domain.Page;
import cle.domain.Resource;
import cle.domain.User;
import cle.filehandler.ResourceUpload;
import cle.validation.ResourceValidator;


@Controller
public class ResourceController {
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
	HibernateSearchResource hibernateSearch;
	@Autowired(required = true)
	PageDao pageDao;
	@Autowired(required = true)
	ResourceValidator resourceValidator;
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "collection/id/{collectionId}/add", method = RequestMethod.GET)
	public ModelAndView addResource(
			@PathVariable("collectionId") int collectionId) {
		ModelAndView mav = new ModelAndView("resource/new");
		mav.addObject(new Resource());
		return mav;
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "collection/id/{collectionId}/add", method = RequestMethod.POST)
	public String registration(@ModelAttribute Resource resource,
			BindingResult result, Model model, SessionStatus status,
			@PathVariable("collectionId") int collectionId) {
		resourceValidator.validate(resource, result);
		if (result.hasErrors()) {
			return "resource/new";
		} else {

			final SecurityContext sc = SecurityContextHolder.getContext();
			final Authentication auth = sc.getAuthentication();

			User ud = (User) auth.getPrincipal();// get the user obj from the
													// session

			resource.getCollections().add(collectionDao.find(collectionId));

			Calendar calendar = Calendar.getInstance(); // set created +
														// modified to
			// now.
			java.util.Date d = calendar.getTime();
			resource.setCreated(d);
			resource.setModified(d);
			resource.setUser(ud);

			resourceDao.saveOrUpdate(resource);
			// ResourceUpload ru = new ResourceUpload();
			boolean convert = resourceUpload.process(resource);
			if (convert == true){
				status.setComplete();
				model.addAttribute("resource", resource);
			resourceDao.saveOrUpdate(resource);

			return "redirect:" + "";
			}
			else {
				resourceDao.delete(resource);
				return "redirect:/error/PDFConversionError";
			}
		}

	}

	@RequestMapping(value = "resource/id/{resourceId}", method = RequestMethod.GET)
	public ModelAndView viewById(
			@PathVariable("resourceId") int resourceId,
			@RequestParam(value = "startpage", required = false, defaultValue = "1") int startPage) {
		ModelAndView mav = new ModelAndView("resource/view");
		Resource r = resourceDao.findResourceAndPages(resourceId);
		int noOfPages = r.getPages().size();

		mav.addObject(r);
		mav.addObject("author", r.getUser());
		mav.addObject("noOfPages", noOfPages);

		mav.addObject("startPage", startPage);
		return mav;
	}

	@RequestMapping(value = "resourceid/{resourceId}/page/{pageNumber}/", method = RequestMethod.GET)
	public ModelAndView getResourceData(
			@PathVariable("resourceId") int resourceId,
			@PathVariable("pageNumber") int pageNumber) {
		ModelAndView mav = new ModelAndView("/iframe/iframe");
		// Resource r = resourceDao.find(resourceId);
		Page p = pageDao.findByResourceAndPage(resourceId, pageNumber);
		if(p == null){
			return new ModelAndView("redirect:/error/pageNotFound");
		}
		// System.out.println("html" + r.getContent());
		mav.addObject(p);
		return mav;
	}

	@RequestMapping(value = "resourceid/{resourceId}/page/{pageNumber}/css/*", method = RequestMethod.GET)
	public RedirectView getResourceCSS(
			@PathVariable("resourceId") int resourceId,
			@PathVariable("pageNumber") int pageNumber) {

		return new RedirectView("/cle/css/resource.css");
	}

	@RequestMapping(value = "/resource/jsonList", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getPageList(@RequestParam("term") String term) {
		ModelMap map = new ModelMap();

		List<Resource> list = resourceDao.searchInTitle(term);

		map.addAttribute(list);
		return map;

	}

	@RequestMapping(value = "/resource/selective/jsonList", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getResourceListExcl(@RequestParam("term") String term,
			@RequestParam("collectionId") String collectionId) {
		ModelMap map = new ModelMap();
		List<Resource> list = resourceDao.searchInTitle(term);

		map.addAttribute(list);
		return map;

	}

	@RequestMapping(value = "/resource/search", method = RequestMethod.POST)
	public ModelAndView searchResource(@RequestParam("terms") String terms) {
		ModelAndView mav = new ModelAndView();
		// List<Resource> resourceList = resourceDao.searchInTitle(terms);
		// Hibernate search more expensive so could be swapped for this
		List<Resource> resourceList = hibernateSearch
				.resourceSearchByTitleTagsDescription(terms);
		mav.addObject(resourceList);
		return mav;

	}
	
	@RequestMapping(value = "/resource/advancedSearch", method = RequestMethod.GET)
	public ModelAndView searchAdvanced() {
		ModelAndView mav = new ModelAndView("resource/advancedSearch");
		
		return mav;

	}
	
	@RequestMapping(value = "/resource/advancedSearch", method = RequestMethod.POST)
	public ModelAndView searchAdvancedPost(@RequestParam("searchTerm") String terms) {
		ModelAndView mav = new ModelAndView("resource/search");
		// List<Resource> resourceList = resourceDao.searchInTitle(terms);
		// Hibernate search more expensive so could be swapped for this
		List<Resource> resourceList = hibernateSearch
				.resourceSearchByTitleTagsDescriptionWildcard(terms);
		mav.addObject(resourceList);
		return mav;

	}
}
