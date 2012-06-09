package cle.feeds;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cle.custom.ActivityItem;
import cle.dao.CommentDao;
import cle.dao.UserDao;
import cle.domain.Comment;
import cle.domain.User;

@Controller
public class UserActivity {
	
	
	@Autowired
	CommentDao commentDao;
	@Autowired
	UserDao userDao;
	@RequestMapping(value = "/user/recentActivity", method = RequestMethod.POST)
	public ModelAndView activityFeed(@RequestParam(value="userId",required=false) int userId){
		ModelAndView mav = new ModelAndView("/feeds/userActivity");
		List<ActivityItem> list = commentDao.findCommentActivityByUser(userId);
		User user = userDao.find(userId);
		
		
	
		mav.addObject("itemList", list);
		mav.addObject("user", user);
		return mav;
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/user/privateComments", method = RequestMethod.GET)
	public ModelAndView privateCommentFeed(){
		
		ModelAndView mav = new ModelAndView("/feeds/comments");
		
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User user = (User) auth.getPrincipal();// get the user obj from the
												// session
		
		
	

		mav.addObject("user", user);
		return mav;
	}
}
