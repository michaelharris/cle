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

import cle.dao.PageDao;
import cle.dao.UserDao;
import cle.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
//specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations={"file:WebContent/WEB-INF/testing-cle-servlet.xml"})
public class HtmlTrial extends Assert implements ApplicationContextAware {
	@Autowired
	UserDao userDao;
	
	@Autowired
	PageDao pageDao;
@Test
	public void tryThis(){
	 User u= userDao.find(1);
	 System.out.println("fname" + u.getFirstName() + " last: " + u.getLastName());
	 
	String contentHTML=  pageDao.find(88).getContent();
	
		addIdAttributeToH2Elements(contentHTML);
		
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}
	
	
	public String addIdAttributeToH2Elements(String html) {
	    Source source = new Source(html);
	   
	    OutputDocument outputDocument = new OutputDocument(source);
	    
	    List<Element> elementList=source.getAllElements();
		for (Element element : elementList) {
			StartTag startTag = element.getStartTag();
	        Attributes attributes = startTag.getAttributes();
	        boolean hasElementId;
	        
	        if(attributes == null || attributes.get("data-elementid") == null){
	        	System.out.println("didn't find the element we were looking for");
	        	System.out.println("st" + startTag);
	        	System.out.println("tidy " + element.getStartTag().tidy());
	        	String newStartTag = element.getStartTag().tidy();
	newStartTag  = newStartTag.substring(0, (newStartTag.length()-1));
	System.out.println("new start" + newStartTag);
	        	  StringBuilder builder = new StringBuilder();
	        	builder.append(newStartTag).append(" ").append("data-elementid=\"").append("10000").append("\"").append(">");
	        	System.out.println("new tag " + builder);
	        	
	        	outputDocument.replace(startTag, builder);//update the tag
	        }
	        else{
	        	//The elementid already exists
//	        	Attribute idAttr =  attributes.get("data-elementid");
//	        	
//	        		System.out.println(idAttr);	
//	        		System.out.println("Source text with content:\n"+element);
	        	}
	        
		}
	    System.out.println("------output ------");
	       
	        //Attribute elemAttribute = attributes.get("data-elementid");
	       // System.out.println("Source text with content:\n"+element);
//	        if (elemAttribute == null) {
//	        	String elementValue = element.getTextExtractor().toString();
//	        	StringBuilder builder = new StringBuilder();
//	        	System.out.println("start" + startTag);
//	        }
	        
			
		
	    
	    
	    
//	    List<Element> divElements = source.getAllElements("div");
//	    
//	    for (Element element : divElements) {
//	    	Attributes a = element.getAttributes();
//	    	for (Attribute att : a){
//	    		System.out.println(att.toString());
//	    	}
//	    	
//	    }
//	    List<Element> elementList=source.getAllElements();
//		for (Element element : elementList) {
//			System.out.println("-------------------------------------------------------------------------------");
//			System.out.println(element.getDebugInfo());
//			if (element.getAttributes()!=null) System.out.println("XHTML StartTag:\n"+element.getStartTag().tidy(true));
//			System.out.println("Source text with content:\n"+element);
//		}
	 
//	    for (Element element : h2Elements) {
//	        StartTag startTag = element.getStartTag();
//	        Attributes attributes = startTag.getAttributes();
//	        Attribute idAttribute = attributes.get("id");
//	 
//	        if (idAttribute == null) {
//	            String elementValue = element.getTextExtractor().toString();
//	            String validAnchorId = AnchorUtils.getLowerCasedValidAnchorTitle(elementValue);
//	 
//	            StringBuilder builder = new StringBuilder();
//	            builder.append("<h2").append(" ").append("id=\"").append(validAnchorId).append("\"");
//	            for (Attribute attribute : attributes) {
//	                builder.append(" ");
//	                builder.append(attribute);
//	            }
//	            builder.append(">");
//	 
//	            outputDocument.replace(startTag, builder);
//	        }
//	    }
	 System.out.println(outputDocument.toString());
	    return outputDocument.toString();
	}
	
	private static void displaySegments(List<? extends Segment> segments) {
		for (Segment segment : segments) {
			System.out.println("-------------------------------------------------------------------------------");
			System.out.println(segment.getDebugInfo());
			System.out.println(segment);
		}
		System.out.println("\n*******************************************************************************\n");
	}
}
