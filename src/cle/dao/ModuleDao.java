package cle.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cle.domain.Collection;
import cle.domain.Module;
@Repository
@Transactional
public class ModuleDao {

	@Autowired(required = true)
	private SessionFactory sessionFactory;

	private static Log log = LogFactory.getLog(ModuleDao.class);
	public ModuleDao(){
		
	}

	public ModuleDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(readOnly = true)
	public Module find(int id) {

		Module module = (Module) sessionFactory.getCurrentSession()
				.get(Module.class, id);
		Hibernate.initialize(module.getCollections());
		return module;
	}
	
	@Transactional(readOnly = true)
	public Module findModuleAndUsers(int id) {

		Module module = (Module) sessionFactory.getCurrentSession()
				.get(Module.class, id);
		Hibernate.initialize(module.getUsers());
		return module;
	}
	

	public void saveOrUpdate(Module module) {

		Date d = Calendar.getInstance().getTime();
		if (module.getCreated() == null){
		module.setCreated(d);
		}
		module.setModified(d);

		sessionFactory.getCurrentSession().saveOrUpdate(module);
	}

	public void delete(Module Module) {

		sessionFactory.getCurrentSession().delete(Module);

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Module> findAll() {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "Module");
		return query.list();

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Module> getRecentlyModified(int limit) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Module m order by m.modified desc");
		query.setMaxResults(limit);
		return query.list();
	}
	
//	@Transactional(readOnly = true)
//	@SuppressWarnings("unchecked")
//	public void addCollectionToModule(int moduleId,int collectionId){
//		Module module = (Module) sessionFactory.getCurrentSession()
//		.get(Module.class, moduleId);
//		Collection collection =(Collection) sessionFactory.getCurrentSession().load(
//				Collection.class, collectionId);
//		
//		module.addCollection(collection);
//		collection.addModule(module);
//		System.out.println("in mod DAO");
//		sessionFactory.getCurrentSession().saveOrUpdate(module);
//		sessionFactory.getCurrentSession().saveOrUpdate(collection);
//	}

}
