package cle.web;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import cle.dao.*;
import cle.domain.*;

import cle.filehandler.ResourceUpload;


@Controller
public class ResourceCreatorController {
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

	@RequestMapping(value = "/resource/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView mav = new ModelAndView("/resource/new");
		mav.addObject(new Resource());
		return mav;

	}

	@RequestMapping(value = "/resource/create", method = RequestMethod.POST)
	public String createSubmit(@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam("tags") String tags,
			@RequestParam("collectionId") int collectionId) {

		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		Calendar calendar = Calendar.getInstance(); // set created +
		// modified to
		// now.
		java.util.Date d = calendar.getTime();

		Resource resource = new Resource(name, tags, description, d, ud);
		resource.setCreated(d);
		resource.setModified(d);
		Collection collection = collectionDao.find(collectionId);
		resourceDao.saveOrUpdate(resource);
		collection.getResources().add(resource);
		
		collectionDao.saveOrUpdate(collection);

		
		int resourceId = resource.getResourceid();
		return "redirect:/resource/id/" + resourceId + "/editor";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/resource/id/{resourceId}/editor", method = RequestMethod.GET)
	public ModelAndView editor(@PathVariable("resourceId") int resourceId) {
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		Resource resource = resourceDao.find(resourceId);
		
		if(resource.getUser().getUserid().equals(ud.getUserid())){
		ModelAndView mav = new ModelAndView("/resource/editor/editor");
	

		List<Page> pageAL = pageDao.findPagesOfResource(resourceId);

		mav.addObject(resource);
		mav.addObject(pageAL);
		mav.addObject("noOfPages", pageAL.size());

		return mav;
		}
		else{
			ModelAndView mav2 = new ModelAndView("redirect:/error/notAuthenticated");
			return mav2;
		}
	}

	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/resource/id/{resourceId}/delete", method = RequestMethod.GET)
	public ModelAndView deleteResource(@PathVariable("resourceId") int resourceId) {
	
		Resource resource = resourceDao.find(resourceId);
		ModelAndView mav = new ModelAndView("resource/confirmDelete");
		mav.addObject(resource);
		
		return mav;
	
	}
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/resource/id/{resourceId}/confirmDelete", method = RequestMethod.POST)
	public ModelAndView confirmedDeleteResource(@PathVariable("resourceId") int resourceId) {
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		Resource resource = resourceDao.find(resourceId);
		
		if(resource.getUser().getUserid().equals(ud.getUserid())){
		ModelAndView mav = new ModelAndView("redirect:/");
		resourceDao.delete(resource);
	
		return mav;
		}
		else{
			ModelAndView mav2 = new ModelAndView("redirect:/error/notAuthenticated");
			return mav2;
		}
	}
	@RequestMapping(value = "resourceid/{resourceId}/page/{pageNumber}/edit", method = RequestMethod.GET)
	public ModelAndView getResourceData(
			@PathVariable("resourceId") int resourceId,
			@PathVariable("pageNumber") int pageNumber) {
		ModelAndView mav = new ModelAndView("/iframe/pageEdit");
		// Resource r = resourceDao.find(resourceId);
		Page p = pageDao.findByResourceAndPage(resourceId, pageNumber);
		// System.out.println("html" + r.getContent());
		mav.addObject(p);
		return mav;
	}

}
