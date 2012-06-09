package domain;


import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Before;

import sun.net.www.http.Hurryable;

import cle.dao.CommentDao;
import cle.dao.HibernateUtil;
import cle.dao.PageDao;
import cle.dao.ResourceDao;
import cle.dao.UserDao;
import cle.domain.Comment;
import cle.domain.Page;
import cle.domain.User;

public class CommentOnPageTests extends TestCase {
//	ResourceDao rDao;
	PageDao pDao; 
	UserDao uDao;
	CommentDao cDao;
	final int resourceId = 1;
	final int pageNumber = 1;
	
	

	public void testComment(){
		
		// rDao = new ResourceDao(HibernateUtil.getSessionFactory());
		pDao = new PageDao(HibernateUtil.getSessionFactory());
		uDao = new UserDao(HibernateUtil.getSessionFactory());
		cDao = new CommentDao(HibernateUtil.getSessionFactory());
		
		Calendar calendar = Calendar.getInstance() ;
		java.util.Date d = calendar.getTime();
		Page p = pDao.findByResourceAndPage(resourceId, pageNumber);
		User user = uDao.find(1);
		Comment c = new Comment();
		c.setUser(user);
		
		//c.setComment("2nd new test comment");
//		Set<Comment> set = new HashSet<Comment>();
//		set.add(c);
//		
		cDao.saveOrUpdate(c);
		
	}
	
}
