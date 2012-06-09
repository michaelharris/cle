package cle.web;

import java.io.BufferedOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cle.dao.ImageDao;
import cle.domain.Image;



@Controller
public class ImageController extends MultiActionController{
	@Autowired
	ImageDao iDao;
	
	protected final static Log log = LogFactory.getLog(ImageController.class);
	@RequestMapping(value = "image/id/{imageId}", method = RequestMethod.GET)
	public ModelAndView fetch(@PathVariable("imageId") int imageId, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		log.debug("fetch() called.");
		
		setCacheSeconds(10);

		Image img = iDao.find(imageId);
		
		
		
		response.setContentType( "image/png" );
	
		response.getOutputStream().write(img.getImagedata());
		response.getOutputStream().flush();

	   return null;
	}
	

	@RequestMapping(value = "resourceid/{resourceId}/page/{pageNo}/{imageName}.{ext}", method = RequestMethod.GET)
	public ModelAndView fetchImage(@PathVariable("resourceId") int resourceId, @PathVariable("ext") String ext, @PathVariable("imageName") String imageName, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		//log.debug("fetchImage() called.");
		
		setCacheSeconds(100);
		String fileName = imageName + "." + ext;
		
		byte[] img = iDao.findByResourceAndName(resourceId, fileName);
		
		
		response.setContentType( "image/png" );
		//BufferedOutputStream out = new BufferedOutputStream( response.getOutputStream() );
		response.getOutputStream().write(img);
		response.getOutputStream().flush();
		response.flushBuffer();
		response.getOutputStream().close();
		img = null;

	   return null;
	}
	
	@RequestMapping(value = "resource/id/{resourceId}/{imageName}.{ext}", method = RequestMethod.GET)
	public ModelAndView fetchImageNoPage(@PathVariable("resourceId") int resourceId, @PathVariable("ext") String ext, @PathVariable("imageName") String imageName, HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		//log.debug("fetchImage() called.");
		
		setCacheSeconds(100);
		String fileName = imageName + "." + ext;
		
		byte[] img = iDao.findByResourceAndName(resourceId, fileName);
		
		
		response.setContentType( "image/png" );
		//BufferedOutputStream out = new BufferedOutputStream( response.getOutputStream() );
		response.getOutputStream().write(img);
		response.getOutputStream().flush();
		response.flushBuffer();
		response.getOutputStream().close();
		img = null;

	   return null;
	}
}