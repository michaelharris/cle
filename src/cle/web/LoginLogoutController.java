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
public class LoginLogoutController {


@RequestMapping(value = "/login", method = RequestMethod.GET)
public String login(){
	
	return "user/login";
}

@RequestMapping(value = "/logout", method = RequestMethod.GET)

public String logout(){
	
	
	
	return "admin/test2";
}


}
