package domain;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import junit.framework.TestCase;

import net.htmlparser.jericho.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cle.dao.PageDao;
import cle.dao.UserDao;
import cle.domain.User;
import cle.filehandler.FileOps;
import cle.filehandler.YouTube;

@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/testing-cle-servlet.xml" })
public class YouTubeTester extends Assert implements ApplicationContextAware {


	@Test
	public void tryThis() {
		String id = "ylLzyHk54Z0";
		String result = YouTube.generateEmbed(id);
		System.out.println(result);
		
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub

	}

}
