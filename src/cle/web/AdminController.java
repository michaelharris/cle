package cle.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.sun.org.apache.xpath.internal.operations.Mod;

import cle.dao.CollectionDao;
import cle.dao.ModuleDao;
import cle.dao.ResourceDao;
import cle.dao.RoleDao;
import cle.dao.UserDao;
import cle.domain.Collection;
import cle.domain.Module;
import cle.domain.Role;
import cle.domain.User;

@Controller
@RequestMapping("/admin/")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
	@Autowired(required = true)
	UserDao uDao;

	@Autowired(required = true)
	CollectionDao cDao;

	@Autowired(required = true)
	ModuleDao mDao;

	@Autowired(required = true)
	ResourceDao rDao;
	@Autowired(required = true)
	RoleDao roleDao;

	// mapping the root (relative to admin)
	@RequestMapping(value = "/")
	public String adminHome() {
		return "admin/home";
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView accessUsers() {
		ModelAndView mav = new ModelAndView("/admin/user/list");

		List<User> userList = uDao.findAll();
		mav.addObject(userList);

		return mav;
	}

	@RequestMapping(value = "/users/delete", method = RequestMethod.POST)
	public String deleteUser(@RequestParam("id") int id) {
		System.out.println("Deleting");
		System.out.println("id " + id);
		uDao.delete(uDao.find(id));
		return "redirect: ";
	}

	@RequestMapping(value = "/users/roleEdit", method = RequestMethod.GET)
	public ModelAndView editUserRoles(@RequestParam("user") int userId) {

		ModelAndView mav = new ModelAndView("/admin/user/roleEdit");

		ArrayList<Role> userRoles = new ArrayList<Role>(uDao.find(userId)
				.getRoles());
		ArrayList<Role> availableRoles = new ArrayList<Role>(roleDao.findAll());
		availableRoles.removeAll(userRoles);

		mav.addObject("user", uDao.find(userId));
		mav.addObject("availableRoles", availableRoles);
		mav.addObject("currentRoles", userRoles);

		return mav;
	}



	@RequestMapping(value = "/users/id/{userId}/roleUpdate", method = RequestMethod.POST)
	public ResponseEntity<String> roleUpdate(
			@PathVariable("userId") int userId, HttpServletRequest request) {

		Set<Role> newRoles = new HashSet<Role>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> map = request.getParameterMap();
		for (String id : map.keySet()) {
			//System.out.println("roleid " + id);
			newRoles.add(roleDao.find(Integer.parseInt(id)));
		}

		User user = uDao.find(userId);
		user.getRoles().clear();

		
		user.setRoles(newRoles);
		uDao.saveOrUpdate(user);

		return new ResponseEntity<String>(HttpStatus.ACCEPTED);

	}

	@RequestMapping(value = "/collections", method = RequestMethod.GET)
	public ModelAndView accessCollections() {
		ModelAndView mav = new ModelAndView("/admin/collection/list");

		List<Collection> collectionList = cDao.findAll();
		mav.addObject(collectionList);

		return mav;
	}

	@RequestMapping(value = "/collections/delete", method = RequestMethod.POST)
	public String deleteCollection(@RequestParam("id") int id) {
		System.out.println("Deleting");
		System.out.println("id " + id);
		cDao.delete(cDao.find(id));

		return "redirect: ";
	}

	@RequestMapping(value = "/collections/{collectionId}/resources/", method = RequestMethod.GET)
	public ModelAndView resourcesByCollection(
			@PathVariable("collectionId") int collectionId) {
		ModelAndView mav = new ModelAndView("/admin/resource/list");
		Collection c = cDao.find(collectionId);
		// mav.addObject(c);

		mav.addObject("resourceList", c.getResources());
		// System.out.println(c.getResources().size());
		return mav;

	}

	// delete resource from post request
	@RequestMapping(value = "/collections/{collectionId}/resources/delete", method = RequestMethod.POST)
	public String deleteResource(
			@PathVariable("collectionId") int collectionId,
			@RequestParam("id") int id) {
		Collection c = cDao.find(collectionId);
		rDao.delete(rDao.find(id));

		return "redirect: ";
	}

	@RequestMapping(value = "/modules", method = RequestMethod.GET)
	public ModelAndView accessModules() {
		ModelAndView mav = new ModelAndView("/admin/module/list");

		List<Module> moduleList = mDao.findAll();
		mav.addObject(moduleList);

		return mav;
	}

	@RequestMapping(value = "/modules/delete", method = RequestMethod.POST)
	public String deleteModule(@RequestParam("id") int id) {
		System.out.println("Deleting");
		System.out.println("id " + id);
		mDao.delete(mDao.find(id));

		return "redirect: ";
	}

	@RequestMapping(value = "/admin/othertest", method = RequestMethod.GET)
	public String accesstest2() {

		return "admin/test2";
	}

	@RequestMapping(value = "/admin/test3", method = RequestMethod.GET)
	public ModelAndView accessAdmin3() {
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();
		User ud = (User) auth.getPrincipal();

		Map m = new HashMap();

		return new ModelAndView("admin/test3", "user", ud);
	}

	@RequestMapping(value = "/roles/", method = RequestMethod.GET)
	public ModelAndView showRoles() {

		ModelAndView mav = new ModelAndView("/admin/role/list");
		mav.addObject("roleList", roleDao.findAll());
		return mav;

	}

	@RequestMapping(value = "/roles/delete", method = RequestMethod.POST)
	public String deleteRole(@RequestParam("id") int id) {

		roleDao.delete(roleDao.find(id));

		return "redirect: ";
	}

	@RequestMapping(value = "/roles/new", method = RequestMethod.POST)
	public String newRole(@RequestParam("roleName") String roleName) {
		Role role = new Role();
		role.setRolename(roleName);
		roleDao.saveOrUpdate(role);

		return "redirect: ";
	}

}
