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
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/testing-cle-servlet.xml" })
public class RoleDaoTests extends Assert implements ApplicationContextAware{ 

User u;
Role r;
@Autowired
UserDao userDao;
@Autowired
RoleDao roleDao;
@After
public void cleanup(){
	//userDao.delete(u);

}


@Test
public void testCreateNewRole(){
	u = new User("junit-email", "pass", "fname", "lname");
	int size1 = roleDao.findAll().size();
	
	
	r = new Role();
	r.setRolename("junit-test-role");
	roleDao.saveOrUpdate(r);
	userDao.saveOrUpdate(u);
	r.getUsers().add(u);
	u.getRoles().add(r);
	
	userDao.saveOrUpdate(u);
	
	int size2 = roleDao.findAll().size();
	
	assertTrue(size2 > size1);
	
	
}
@Override
public void setApplicationContext(ApplicationContext arg0)
		throws BeansException {
	// TODO Auto-generated method stub
	
}



}