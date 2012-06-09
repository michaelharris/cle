package domain;

import java.util.Calendar;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cle.dao.*;
import cle.domain.*;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/testing-cle-servlet.xml" })
public class CollectionDaoTests extends Assert implements
		ApplicationContextAware {

	Collection c;
	User u;

	@Autowired
	UserDao userDao;
	@Autowired
	CollectionDao collectionDao;

	@After
	public void cleanup() {
		collectionDao.delete(c);
		userDao.delete(u);

	}

	@Test
	public void testCreateNewCollection() {
		int size1 = collectionDao.findAll().size();
		u = new User("junit-email-collection", "pass", "fname", "lname");
		userDao.saveOrUpdate(u);
		c = new Collection(u, "junit test title", Calendar.getInstance()
				.getTime());

		collectionDao.saveOrUpdate(c);
		int size2 = collectionDao.findAll().size();

		assertTrue(size2 > size1);

	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub

	}

}
