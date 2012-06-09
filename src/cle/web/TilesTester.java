package cle.web;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class TilesTester {
	@RequestMapping(value = "/book/search", method = RequestMethod.GET)
	public String bookPage(){
		
		return "book/search";
	}
	
	@RequestMapping(value = "/book/test", method = RequestMethod.GET)
	public String test(){
		ModelAndView mv = new ModelAndView();

		
		return "book/test";
	}

}
