package cle.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import cle.dao.CommentDao;
import cle.dao.HibernateUtil;
import cle.dao.ModuleDao;
import cle.dao.PageDao;
import cle.dao.ResourceDao;
import cle.dao.UserDao;
import cle.domain.Collection;
import cle.domain.Comment;
import cle.domain.Module;
import cle.domain.Page;
import cle.domain.Resource;
import cle.domain.User;

@Controller
public class CommentController {
	@Autowired(required = true)
	UserDao userDao;
	@Autowired(required = true)
	ModuleDao moduleDao;
	@Autowired(required = true)
	CollectionDao collectionDao;
	@Autowired(required = true)
	ResourceDao resourceDao;
	@Autowired(required = true)
	CommentDao commentDao;
	@Autowired(required = true)
	PageDao pageDao;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/comment/new", method = RequestMethod.POST)
	public ResponseEntity<String> newComment(HttpServletRequest request,
			@RequestParam("divId") int divId, @RequestParam("location") String location,
			@RequestParam("commentdata") String commentdata,
			@RequestParam("pId") int pageNo, @RequestParam("resId") int resId,
			@RequestParam("privacy") int privacy) {
		// System.out.println("New Comment Method");
		// System.out.println("id " + divId + "comment " + commentdata + " res "
		// + resId + " page " + pageNo);
		// String referer = request.getHeader("Referer");

		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		Date d = Calendar.getInstance().getTime();
		Comment comment = new Comment(ud, commentdata, d);

		comment.setPage(pageDao.findByResourceAndPage(resId, pageNo));
		comment.setResource(resourceDao.find(resId));
		comment.setElementid(divId);

		comment.setLocation(location);
		
		comment.setVisibility(privacy);
		comment.setCreated(d);
		commentDao.saveOrUpdate(comment);

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/comment/new/onComment", method = RequestMethod.POST)
	public ResponseEntity<String> newCommentOnComment(
			HttpServletRequest request, @RequestParam("parentId") int parentId,
			@RequestParam("commentdata") String commentdata) {

		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session
		Date d = Calendar.getInstance().getTime();
		Comment comment = new Comment(ud, commentdata, d);
		comment.setComment(commentDao.find(parentId));// Set the element that
														// this is a child of.

		comment.setCreated(d);
		commentDao.saveOrUpdate(comment);

		return new ResponseEntity<String>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/comment/stream", method = RequestMethod.GET)
	public @ResponseBody
	List<Comment> getCommentStream(@RequestParam int page, @RequestParam int res) {

		ArrayList<Comment> commentList = new ArrayList<Comment>();

		commentList.addAll(commentDao.findPublicComments(res, page));

		return commentList;

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/comment/privateStream", method = RequestMethod.GET)
	public @ResponseBody
	List<Comment> getPrivateCommentStream(@RequestParam int page,
			@RequestParam int res) {

		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();

		User ud = (User) auth.getPrincipal();// get the user obj from the
												// session

		ArrayList<Comment> commentList = new ArrayList<Comment>();

		commentList.addAll(commentDao.findPrivateComments(ud.getUserid(), res,
				page));

		return commentList;

	}

	@RequestMapping(value = "/comment/id/{commentId}/stream", method = RequestMethod.GET)
	public @ResponseBody
	List<Comment> getCommentStream(@PathVariable("commentId") int commentId) {

		ArrayList<Comment> commentList = new ArrayList<Comment>();
		commentList.addAll(commentDao.findSubComments(commentId));

		return commentList;

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/comment/delete/id/{commentId}", method = RequestMethod.GET)
	public ResponseEntity<String> deleteById(
			@PathVariable("commentId") int commentId) {
		ModelAndView mav = new ModelAndView("resource/view");
		final SecurityContext sc = SecurityContextHolder.getContext();
		final Authentication auth = sc.getAuthentication();
		User ud = (User) auth.getPrincipal();// get the user obj from the
		Comment comment = commentDao.find(commentId);
		// auth.getAuthorities().
		if (comment.getUser().getUserid().equals(ud.getUserid())) {

			commentDao.delete(comment);
			return new ResponseEntity<String>(HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

}