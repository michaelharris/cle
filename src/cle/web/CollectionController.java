package cle.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import cle.dao.CollectionDao;
import cle.dao.HibernateUtil;
import cle.dao.ModuleDao;
import cle.dao.ResourceDao;
import cle.dao.UserDao;
import cle.domain.Collection;
import cle.domain.Comment;
import cle.domain.Module;
import cle.domain.Page;
import cle.domain.Resource;
import cle.domain.User;
import cle.validation.CollectionValidator;
import cle.validation.ModuleValidator;
import cle.validation.UserValidator;

@Controller
public class CollectionController {
	@Autowired(required = true)
	UserDao userDao;
	@Autowired(required = true)
	ModuleDao moduleDao;
	@Autowired(required = true)
	CollectionDao collectionDao;
	@Autowired(required = true)
	ResourceDao resourceDao;
	@Autowired(required = true)
	CollectionValidator collectionValidator;

	public void setUserDao(UserDao u) {
		this.userDao = u;
	}

	public void setCollectionDao(CollectionDao cDao) {
		this.collectionDao = cDao;
	}

	public void setModuleDao(ModuleDao m) {
		this.moduleDao = m;
	}

	public void setResourceDao(ResourceDao r) {
		this.resourceDao = r;
	}

	@RequestMapping(value = "/collection/new", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public void newCollection(Model model) {

		model.addAttribute(new Collection());

	}

	@RequestMapping(value = "/collection/new", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String create(@ModelAttribute Collection collection,
			BindingResult result, Model model, SessionStatus status) {

		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		collection.setUser(ud);
		Calendar calendar = Calendar.getInstance(); // set created + modified to
													// now.
		java.util.Date d = calendar.getTime();
		collection.setModified(d);
		collection.setCreated(d);

		collectionValidator.validate(collection, result); // now validate
															// everything

		if (result.hasErrors()) {

			return "collection/new";
		} else {

			status.setComplete();
			model.addAttribute("collection", collection);

			collectionDao.saveOrUpdate(collection);

			// return "redirect:" + collection.getCollectionid();
			return "redirect:/";
		}

	}

	/**
	 * Custom handler for displaying a collection
	 * 
	 * @param collectionId
	 *            the ID of the owner to display
	 * @return a ModelAndView with the model attributes for the view
	 */
	@RequestMapping("collection/id/{collectionId}")
	public ModelAndView viewById(@PathVariable("collectionId") int collectionId) {
		ModelAndView mav = new ModelAndView("collection/view");
		Collection c = collectionDao.find(collectionId);
		mav.addObject(c);
		mav.addObject("resourceList", c.getResources());
		return mav;
	}

	@RequestMapping("collection/id/{collectionId}/delete")
	public ModelAndView delete(@PathVariable("collectionId") int collectionId) {
		ModelAndView mav = new ModelAndView("collection/confirmDelete");
		Collection c = collectionDao.find(collectionId);
		mav.addObject(c);

		return mav;
	}

	@RequestMapping("collection/id/{collectionId}/confirmDelete")
	public ModelAndView confirmDeleteCollection(
			@PathVariable("collectionId") int collectionId) {
		Collection c = collectionDao.find(collectionId);
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		if (c.getUser().getUserid().equals(ud.getUserid())) {
			ModelAndView mav = new ModelAndView("redirect:/");
			collectionDao.delete(c);
			return mav;
		} else {
			ModelAndView mav2 = new ModelAndView(
					"redirect:/error/notAuthenticated");
			return mav2;
		}
	}

	@RequestMapping("collection/list")
	public ModelAndView viewAll(
			@RequestParam(value = "start", required = false, defaultValue = "0") int start,
			@RequestParam(value = "number", required = false, defaultValue = "5") int number) {
		ModelAndView mav = new ModelAndView("collection/list");
		int nextStart = start + number + 1;// For the next button on the view
		mav.addObject("collectionList", collectionDao.paging(start, number));
		mav.addObject("nextStart", nextStart);
		mav.addObject("start", start);
		mav.addObject("number", number);
		mav.addObject("prevStart", nextStart - (2 * (number + 1)));
		return mav;
	}

	@RequestMapping(value = "/collection/search", method = RequestMethod.POST)
	public ModelAndView searchCollection(@RequestParam("terms") String terms) {
		ModelAndView mav = new ModelAndView();
		List<Collection> collectionList = collectionDao.searchInTitle(terms);

		mav.addObject(collectionList);
		return mav;

	}

	@RequestMapping(value = "collection/id/{collectionId}/details", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getCommentStream(@PathVariable("collectionId") int collectionId) {

		Collection c = collectionDao.find(collectionId);
		User u = c.getUser();
		// ArrayList<Comment> commentList = new ArrayList<Comment>();

		// Page p = pageDao.findByResourceAndPage(res, page);

		// commentList.addAll(p.getComments());
		ModelMap map = new ModelMap();
		map.addAttribute("title", c.getTitle());
		map.addAttribute("ownerName", u.getFirstName() + " " + u.getLastName());
		// map.addAttribute("ownerId", c.getUser().getFirstName() +
		// c.getUser().getUserid());

		return map;

	}

	@RequestMapping("collection/id/{collectionId}/remove/resource/id/{resourceId}")
	public ModelAndView removeResourceFromcollection(
			@PathVariable("collectionId") int collectionId,
			@PathVariable("resourceId") int resourceId) {
		Collection c = collectionDao.find(collectionId);
		
		Resource resource = resourceDao.findWithCollections(resourceId);
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		if (c.getUser().getUserid().equals(ud.getUserid())) {
			c.removeResource(resource);
			resource.removeCollection(c);
			c.removeResource(resource);
			resource.removeCollection(c);
			collectionDao.saveOrUpdate(c);
			resourceDao.saveOrUpdate(resource);
			System.out.println("saved changes");
			ModelAndView mav2 = new ModelAndView(
			"redirect:/collection/id/"+ collectionId);
	return mav2;
		}
		ModelAndView mav2 = new ModelAndView(
		"redirect:/error/notAuthenticated");
return mav2;
	}

	@RequestMapping(value = "/collection/jsonList", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getPageList(@RequestParam("term") String term) {
		ModelMap map = new ModelMap();
		List<Collection> list = collectionDao.searchInTitle(term);

		map.addAttribute(list);
		return map;

	}

	@RequestMapping(value = "/collection/selective/jsonList", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getPageListExcl(@RequestParam("term") String term,
			@RequestParam("moduleId") String moduleId) {
		ModelMap map = new ModelMap();
		List<Collection> list = collectionDao.searchInTitle(term);

		map.addAttribute(list);
		return map;

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/collection/addResource", method = RequestMethod.POST)
	public ResponseEntity<String> addResourceToCollection(
			@RequestParam("collectionId") int collectionId,
			@RequestParam("resourceId") int resourceId) {

		Resource resource = resourceDao.findWithCollections(resourceId);
		Collection collection = collectionDao.find(collectionId);
		for (Collection c : resource.getCollections()) {
			if (c.getCollectionid().equals(collectionId)) {

				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);

			}
		}

		collection.getResources().add(resource);
		resource.getCollections().add(collection);

		try {
			collectionDao.saveOrUpdate(collection);
		} catch (Exception e) {
			System.out
					.println("had to catch exception couldn't add resource to collection");
		}

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/collection/jsonResourceList", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getPageList(@RequestParam("collectionId") int collectionId) {
		ModelMap map = new ModelMap();
		Collection collection = collectionDao.find(collectionId);
		Set<Resource> set = collection.getResources();

		map.addAttribute(set);
		return map;

	}

}