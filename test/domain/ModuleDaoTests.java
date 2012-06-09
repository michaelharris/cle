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
public class ModuleDaoTests extends Assert implements ApplicationContextAware {

	Module m;
	@Autowired
	ModuleDao moduleDao;

	@After
	public void cleanup() {
		moduleDao.delete(m);

	}

	@Test
	public void testCreateNewModule() {
		int size1 = moduleDao.findAll().size();

		m = new Module(Calendar.getInstance().getTime());
		m.setTitle("junit test module2");
		moduleDao.saveOrUpdate(m);
		int size2 = moduleDao.findAll().size();

		assertTrue(size2 > size1);

	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub

	}

}
