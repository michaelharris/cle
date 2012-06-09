package cle.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cle.domain.Role;
@Repository
@Transactional
public class RoleDao {
	@Autowired(required = true)
	private SessionFactory sessionFactory;
	private static Log log = LogFactory.getLog(RoleDao.class);

	public RoleDao(){
	}
	
	public RoleDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired(required = true)
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Role find(int id) {
		return (Role) sessionFactory.getCurrentSession().get(Role.class, id);
	}

	public void saveOrUpdate(Role Role) {

		sessionFactory.getCurrentSession().saveOrUpdate(Role);
	}

	public void delete(Role Role) {

		sessionFactory.getCurrentSession().delete(Role);

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Role> findAll() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "Role");
		return query.list();

	}

}
