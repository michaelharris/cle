package cle.web;

import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@RequestMapping(value = "error/notAuthenticated", method = RequestMethod.GET)
	public ModelAndView notAuthenticated() {
		ModelAndView mav = new ModelAndView("error/notAuthenticated");

		return mav;
	}

	@RequestMapping(value = "error/PDFConversionError", method = RequestMethod.GET)
	public ModelAndView PDFConversionError() {
		ModelAndView mav = new ModelAndView("error/badPDF");
		return mav;
	}

	@RequestMapping(value = "error/pageNotFound", method = RequestMethod.GET)
	public ModelAndView pageNotFoundError() {
		ModelAndView mav = new ModelAndView("/error_plain/pageNotFound");
		return mav;
	}

}
