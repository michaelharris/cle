package cle.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import cle.dao.CollectionDao;
import cle.dao.HibernateUtil;
import cle.dao.ModuleDao;
import cle.dao.UserDao;
import cle.domain.Collection;
import cle.domain.Module;
import cle.domain.User;
import cle.validation.CollectionValidator;
import cle.validation.ModuleValidator;
import cle.validation.UserValidator;

@Controller

public class CollectionViewController {
	@Autowired(required = true)
	UserDao userDao;
	@Autowired(required = true)
	ModuleDao moduleDao;
	@Autowired(required = true)
	CollectionDao collectionDao;

	
	public void setUserDao(UserDao u) {
		this.userDao = u;
	}
	public void setCollectionDao(CollectionDao cDao) {
		this.collectionDao = cDao;
	}


	public void setModuleDao(ModuleDao m) {
		this.moduleDao = m;
	}

	@RequestMapping(value = "/collection/view/{id}", method = RequestMethod.GET)	
	public ModelAndView newCollection(@PathVariable("id") int id) {
		return null;

	}


	

}