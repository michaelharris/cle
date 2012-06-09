package cle.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cle.domain.User;
@Controller
public class AdminControllerTester {


@RequestMapping(value = "/admin/test", method = RequestMethod.GET)
public String accessAdmin(){
	
	return "admin/test";
}

@RequestMapping(value = "/admin/othertest", method = RequestMethod.GET)

public String accesstest2(){
	
	
	
	return "admin/test2";
}


@RequestMapping(value = "/admin/test3", method = RequestMethod.GET)
public ModelAndView accessAdmin3(){
	final SecurityContext sc = SecurityContextHolder.getContext();
	final Authentication auth = sc.getAuthentication();
	User ud = (User) auth.getPrincipal();
	
	Map m = new HashMap();
	
	
	
	return new ModelAndView("admin/test3", "user", ud);
}
}
