package cle.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import cle.domain.Image;
import cle.domain.Page;
import cle.domain.Resource;

@Repository
@Transactional
public class PageDao {
	@Autowired(required = true)
	private SessionFactory sessionFactory;
	private static Log log = LogFactory.getLog(PageDao.class);

	public PageDao() {

	}

	public PageDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired(required = true)
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Page findByResourceAndPage(int resourceId, int pageNumber) {
		List<Page> objects = null;

		// This gets all of the images from a resource. - hopefully with caching
		// the will mean fewer db hits.?
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from "
								+ "Page as p "
								+ "where p.resource.resourceid = ? and p.pagenumber = ?");

		query.setParameter(0, resourceId);
		query.setParameter(1, pageNumber);
		objects = query.list();
		if(objects.size() < 1)		return null;//page could not be found
		return objects.get(0);

	}

	@Transactional(readOnly = true)
	public Page find(int id) {

		return (Page) sessionFactory.getCurrentSession().get(Page.class, id);
	}

	public void saveOrUpdate(Page page) {
	//	Hibernate.initialize(page.getResource());
	//	Resource r = page.getResource();
		
		Date d = Calendar.getInstance().getTime();
	//	r.setModified(d);
	//	sessionFactory.getCurrentSession().saveOrUpdate(r);
		page.setModified(d);
		sessionFactory.getCurrentSession().saveOrUpdate(page);
	}

	public void delete(Page Page) {

		sessionFactory.getCurrentSession().delete(Page);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Page> findAll() {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "Page");
		return query.list();

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Page pageFromResourceAndNumber(int resId, int pageNo) {
		List<Page> objects = null;

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Page as p where p.resource.resourceid = ? and p.pagenumber = ?");
		query.setParameter(0, resId);
		query.setParameter(1, pageNo);
		objects = query.list();

		return objects.get(0);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Page> findPagesOfResource(int resourceId) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Page as p where p.resource.resourceid = ? order by p.pagenumber ASC");
		query.setParameter(0, resourceId);

		return query.list();

	}
}
