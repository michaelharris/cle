package domain;

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

import cle.dao.HibernateSearchResource;
import cle.dao.PageDao;
import cle.dao.UserDao;
import cle.domain.Resource;
import cle.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"file:WebContent/WEB-INF/testing-cle-servlet.xml"})
public class HibernateSearchTests extends Assert implements ApplicationContextAware {
	@Autowired
	UserDao userDao;
	
	@Autowired
	PageDao pageDao;
	
	@Autowired
	HibernateSearchResource hibernateSearch;
@Test
	public void searchForResource(){

	List<Resource> list = hibernateSearch.resourceSearchByTitleTags("yt");
	assertNotNull(list);
	System.out.println("printing list by term yt");
	for(Resource r : list){
		System.out.println(r.getTitle());
	}
		
	}

@Test
public void searchForResourceIncDescription(){

List<Resource> list = hibernateSearch.resourceSearchByTitleTagsDescription("loo?");
assertNotNull(list);
System.out.println("printing list \"2\" in description");
for(Resource r : list){
	System.out.println(r.getTitle());
}
	
}

@Test
public void searchForResourceIncPage(){

List<Resource> list = hibernateSearch.resourceSearchInPage("new");
assertNotNull(list);
System.out.println("printing list \"2\" within page");
for(Resource r : list){
	System.out.println(r.getTitle());
}
	
}
//@Test
//public void rebuildIndex(){
//	hibernateSearch.rebuild();
//}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
