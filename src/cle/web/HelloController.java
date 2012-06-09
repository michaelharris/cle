package cle.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HelloController implements Controller {
	 protected final Log logger = LogFactory.getLog(getClass());
	@Override
	public ModelAndView handleRequest(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		 String now = (new java.util.Date()).toString();
	        logger.info("returning hello view with " + now);
	        return new ModelAndView("hello");
	}

}
