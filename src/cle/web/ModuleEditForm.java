package cle.web;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import cle.dao.ModuleDao;
import cle.dao.UserDao;
import cle.domain.Module;
import cle.validation.ModuleValidator;

/**
 * JavaBean Form controller that is used to edit an existing <code>Module</code>.
 * 
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/module/{moduleId}/edit")
@SessionAttributes(types = Module.class)
public class ModuleEditForm {
	
		@Autowired(required = true)
		UserDao userDao;
		@Autowired(required = true)
		ModuleDao moduleDao;

		
		public void setUserDao(UserDao u) {
			this.userDao = u;
		}


		public void setModuleDao(ModuleDao m) {
			this.moduleDao = m;
		}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(@PathVariable("moduleId") int moduleId, Model model) {
		Module module = moduleDao.find(moduleId);
		model.addAttribute(module);
		return "module/new";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute Module module, BindingResult result, SessionStatus status) {
		new ModuleValidator().validate(module, result);
		if (result.hasErrors()) {
			return "module/new";
		}
		else {
			moduleDao.saveOrUpdate(module);
			status.setComplete();
			return "redirect:/module/" + module.getModuleid();
		}
	}

}
