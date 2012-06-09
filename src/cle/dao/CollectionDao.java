package cle.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cle.domain.Collection;

@Repository
@Transactional
public class CollectionDao {
	@Autowired(required = true)
	private SessionFactory sessionFactory;

	private static Log log = LogFactory.getLog(CollectionDao.class);

	public CollectionDao() {

	}

	public CollectionDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired(required = true)
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(readOnly = true)
	public Collection find(int id) {
Collection c;
		c=(Collection) sessionFactory.getCurrentSession().load(
				Collection.class, id);
		
		Hibernate.initialize(c.getResources());
		
		return c;

	}
	
	@Transactional(readOnly = true)
	public Collection findWithModules(int id) {
Collection c;
		c=(Collection) sessionFactory.getCurrentSession().load(
				Collection.class, id);
		
		Hibernate.initialize(c.getResources());
		Hibernate.initialize(c.getModules());
		
		return c;

	}

	public void saveOrUpdate(Collection collection) {
		Date d = Calendar.getInstance().getTime();
		
		collection.setModified(d);
		
		sessionFactory.getCurrentSession().saveOrUpdate(collection);

	}
	public void modified(Collection collection){
		Date d = Calendar.getInstance().getTime();
		
		collection.setModified(d);
		
		sessionFactory.getCurrentSession().saveOrUpdate(collection);
	}

	public void delete(Collection Collection) {

		sessionFactory.getCurrentSession().delete(Collection);

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Collection> findAll() {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "Collection");

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Collection> getRecentlyModified(int limit) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Collection as c order by c.modified desc");
		query.setMaxResults(limit);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Collection> searchInTitle(String term) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Collection as c where c.title like ?");
		query.setString(0, "%" + term + "%");

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Collection> paging(int start, int number) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Collection as c");
		query.setFirstResult(start);
		query.setMaxResults(number);

		return query.list();

	}

}
