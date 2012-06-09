package cle.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cle.dao.*;

import cle.domain.*;
@Controller
public class RootController {
	@Autowired
	CollectionDao collectionDao;
	@Autowired
	ResourceDao resourceDao;

	@Autowired
	ModuleDao moduleDao;

@RequestMapping(value = "home", method = RequestMethod.GET)
public ModelAndView accessAdmin(){
	ModelAndView mav = new ModelAndView("/home");
	List<Collection> collectionList =  collectionDao.getRecentlyModified(5);
	List<Module> moduleList =  moduleDao.getRecentlyModified(5);
	List<Resource> resourceList =  resourceDao.getRecentlyModified(5);
	mav.addObject(collectionList);
	mav.addObject(moduleList);
	mav.addObject(resourceList);
	
	return mav;
}


}
