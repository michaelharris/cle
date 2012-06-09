package cle.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
import cle.dao.ModuleDao;
import cle.dao.UserDao;

import cle.domain.*;
import cle.validation.ModuleValidator;

@Controller
public class ModuleController {
	@Autowired(required = true)
	UserDao userDao;
	@Autowired(required = true)
	ModuleDao moduleDao;
	@Autowired(required = true)
	CollectionDao collectionDao;
	@Autowired(required = true)
	ModuleValidator moduleValidator;

	public void setUserDao(UserDao u) {
		this.userDao = u;
	}

	public void setModuleDao(ModuleDao m) {
		this.moduleDao = m;
	}

	@RequestMapping(value = "/module/new", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_LECTURER')")
	public void newModuleForm(Model model) {
		Module module = new Module();
		Date d = Calendar.getInstance().getTime();
		module.setCreated(d);

		module.setModified(d);
		model.addAttribute(new Module());

	}

	@PreAuthorize("hasRole('ROLE_LECTURER')")
	@RequestMapping(value = "/module/new", method = RequestMethod.POST)
	public String registration(@ModelAttribute Module module,
			BindingResult result, Model model, SessionStatus status) {
		moduleValidator.validate(module, result);

		if (result.hasErrors()) {
			return "module/new";
		} else {
			// this.clinic.storeOwner(owner);
			status.setComplete();
			model.addAttribute("module", module);
			final SecurityContext sc = SecurityContextHolder.getContext();
			final Authentication auth = sc.getAuthentication();

			User ud = (User) auth.getPrincipal();// get the user obj from the
			HashSet<User> hs = new HashSet<User>();// session
			hs.add(ud);
			module.setUsers(hs);// need to give the module an "owner"
			// in this case someone in the user group who is a lecturer

			// uDao.setSessionFactory(HibernateUtil.getSessionFactory());
			moduleDao.saveOrUpdate(module);

			return "redirect:id/" + module.getModuleid();
		}

	}

	/**
	 * Custom handler for displaying an owner.
	 * 
	 * @param moduleId
	 *            the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@RequestMapping("module/id/{moduleId}")
	public ModelAndView viewById(@PathVariable("moduleId") int moduleId) {
		ModelAndView mav = new ModelAndView("module/view");

		mav.addObject(moduleDao.find(moduleId));
		return mav;
	}

	@RequestMapping("module/list")
	public ModelAndView viewAll() {
		ModelAndView mav = new ModelAndView("module/list");
		mav.addObject(moduleDao.findAll());
		return mav;
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/module/addCollection", method = RequestMethod.POST)
	public ResponseEntity<String> addCollectionToModule(
			@RequestParam("collectionId") int collectionId,
			@RequestParam("moduleId") int moduleId) {

		Module module = moduleDao.find(moduleId);
		Collection collection = collectionDao.findWithModules(collectionId);

		// Check to see if the module already has the collection specified
		for (Module m : collection.getModules()) {
			if (m.getModuleid().equals((moduleId))) {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		}

		module.getCollections().add(collection);
		collection.getModules().add(module);

		moduleDao.saveOrUpdate(module);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/module/jsonCollectionList", method = RequestMethod.GET)
	public @ResponseBody
	ModelMap getPageList(@RequestParam("moduleId") int moduleId) {
		ModelMap map = new ModelMap();
		Module module = moduleDao.find(moduleId);
		Set<Collection> set = module.getCollections();

		map.addAttribute(set);
		return map;

	}

	@PreAuthorize("hasRole('ROLE_LECTURER')")
	@RequestMapping("module/id/{moduleId}/delete")
	public ModelAndView delete(@PathVariable("moduleId") int moduleId) {
		ModelAndView mav = new ModelAndView("module/confirmDelete");
		Module m = moduleDao.find(moduleId);
		mav.addObject(m);

		return mav;
	}

	@PreAuthorize("hasRole('ROLE_LECTURER')")
	@RequestMapping("module/id/{moduleId}/confirmDelete")
	public ModelAndView confirmDeletemodule(
			@PathVariable("moduleId") int moduleId) {
		Module m = moduleDao.findModuleAndUsers(moduleId);
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
										System.out.println("size "+ m.getUsers().size());		// session
		if (m.getUsers().contains(ud)) {
			ModelAndView mav = new ModelAndView("redirect:/");
			moduleDao.delete(m);
			return mav;
		} else {
			ModelAndView mav2 = new ModelAndView(
					"redirect:/error/notAuthenticated");
			return mav2;
		}
	}

}