package cle.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cle.domain.User;

import java.util.List;

public abstract class AbstractSpringDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	private Transaction tx;
	private static Log log = LogFactory.getLog(AbstractSpringDao.class);

	public AbstractSpringDao(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public void setSessionFactory( SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		
	}

	protected void saveOrUpdate(Object obj) {
		try {
			startOperation();
			sessionFactory.getCurrentSession().saveOrUpdate(obj);
			tx.commit();
			
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			// session.close();
		}
	}

	protected void delete(Object obj) {
		try {
			startOperation();
			sessionFactory.getCurrentSession().delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			// session.close();
		}
	}

	protected Object find(Class clazz, int id) {

		Object obj = null;
		try {
			startOperation();

			obj = sessionFactory.getCurrentSession().get(clazz, id);

			tx.commit();
		} catch (HibernateException e) {

			handleException(e);
		} finally {

			// session.close();
		}
		return obj;
	}

	protected List findAll(Class clazz) {
		List objects = null;
		try {
			startOperation();
			Query query = sessionFactory.getCurrentSession().createQuery("from " + clazz.getName());
			objects = query.list();
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			//session.close();
		}
		return objects;
	}

	protected void handleException(HibernateException e)
			throws DataAccessException {

		try {
			if (tx != null) {
				tx.rollback();
			}
		} catch (HibernateException ignored) {
			log.error("Couldn't rollback Transaction", ignored);
		}
	}

	protected void startOperation() throws HibernateException {

		tx = sessionFactory.getCurrentSession().beginTransaction();
		
	}
}