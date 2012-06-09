package cle.web;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import cle.dao.HibernateUtil;
import cle.dao.UserDao;
import cle.domain.Module;
import cle.domain.User;
import cle.validation.UserValidator;

@Controller
public class UserController {
	@Autowired(required = true)
	UserDao userDao;
	
	@Autowired(required = true)
	UserValidator userValidator;
@Autowired
ShaPasswordEncoder passwordEncoder;
	public void setUserDao(UserDao u) {
		this.userDao = u;
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public void registrationForm(Model model) {

		model.addAttribute(new User());

	}

	@RequestMapping(value = "/user/details", method = RequestMethod.POST)
	public String registration(@ModelAttribute User user, BindingResult result,
			Model model, SessionStatus status) {
		//new UserValidator().validate(user, result);
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			
			return "user/register";
		} else {
			
			
			status.setComplete();
			model.addAttribute("user", user);
			String encPassword =  
		        passwordEncoder.encodePassword(user.getPassword(), null);
			user.setPassword(encPassword);
			
			userDao.saveOrUpdate(user);

			return "user/details";
		}

	}

	@RequestMapping("user/id/{userId}")
	public ModelAndView showUserById(@PathVariable("userId") int userId) {

		ModelAndView mav = new ModelAndView("user/view");
		User u = userDao.find(userId);
		u.getRoles().size();
		mav.addObject(u);
		return mav;
	}

	@RequestMapping("user/list")
	public ModelAndView showAllUsers() {

		ModelAndView mav = new ModelAndView("user/list");

		mav.addObject("userList", userDao.findAll());
		return mav;

	}
}