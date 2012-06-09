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

@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/testing-cle-servlet.xml" })
public class FileStoring extends Assert implements ApplicationContextAware {
	@Autowired
	UserDao userDao;

	@Autowired
	PageDao pageDao;

	@Test
	public void tryThis() {
		File temp = null;
		try {
			temp = FileOps.createTempDir();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("abs path" + temp.getAbsolutePath());
		File f = new File("/home/michael/baz/");

		if (temp.exists() == false) {
			System.out.println("doesn't exist");
		}
		if (temp.listFiles() == null) {
			System.out.println("null");
		}

		for (File subFile : temp.listFiles()) {
			System.out.println("indir" + subFile.getAbsolutePath());
		}

		File newFile = new File(temp + "/new1");
		 
		   try {
		    	 Writer output = new BufferedWriter(new FileWriter(newFile));
		      //FileWriter always assumes default encoding is OK!
		      output.write( "hello" );
		      output.close();
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    finally {
		      
		    }

		for (File subFile : temp.listFiles()) {
			System.out.println("indir" + subFile.getAbsolutePath());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub

	}

}
