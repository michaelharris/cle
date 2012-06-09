package cle.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

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
import cle.domain.Resource;

@Repository
@Transactional
public class ResourceDao {
	@Autowired(required = true)
	private SessionFactory sessionFactory;

	private static Log log = LogFactory.getLog(ResourceDao.class);

	public ResourceDao() {

	}

	public ResourceDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired(required = true)
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Resource> findAllInCollection(int collectionId) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "Resource where ");
		return query.list();
	}

	@Transactional(readOnly = true)
	public Resource find(int id) {

		Resource resource = (Resource) sessionFactory.getCurrentSession().get(
				Resource.class, id);

		return resource;
	}

	@Transactional(readOnly = true)
	public Resource findWithCollections(int id) {

		Resource resource = (Resource) sessionFactory.getCurrentSession().get(
				Resource.class, id);
		Hibernate.initialize(resource.getCollections());
		return resource;
	}

	@Transactional(readOnly = true)
	public Resource findResourceAndPages(int id) {

		Resource resource = (Resource) sessionFactory.getCurrentSession().get(
				Resource.class, id);
		Hibernate.initialize(resource.getPages());

		return resource;
	}

	public void saveOrUpdate(Resource resource) {
		Set<Collection> set = resource.getCollections();
		for (Collection c : set) {
			c.setModified(Calendar.getInstance().getTime());
			sessionFactory.getCurrentSession().saveOrUpdate(c);
		}
		sessionFactory.getCurrentSession().saveOrUpdate(resource);
	}

	public void delete(Resource Resource) {

		sessionFactory.getCurrentSession().delete(Resource);

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Resource> findAll() {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "Resource");
		return query.list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Resource> searchInTitle(String term) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Resource as r where r.title like ?");
		query.setString(0, "%" + term + "%");

		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Resource> getRecentlyModified(int limit) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Resource as r order by r.modified desc");
		query.setMaxResults(limit);
		return query.list();
	}

}
