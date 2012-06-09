package cle.dao;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cle.domain.Role;
import cle.domain.User;

@Repository
@Transactional
public class UserDao {
	@Autowired(required = true)
	private SessionFactory sessionFactory;
	private static Log log = LogFactory.getLog(UserDao.class);

	public UserDao() {

	}

	public UserDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public User find(int id) {

		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	public void saveOrUpdate(User user) {

		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	public void delete(User user) {

		sessionFactory.getCurrentSession().delete(user);
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "User");
		return query.list();
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		User user = null;

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "User as user where user.email = ?");
		query.setParameter(0, email);

		try {
			user = (User) query.list().get(0);
		} catch (Exception ex) {
			throw new UsernameNotFoundException("Invalid login", ex);
		}
		if (user == null) {
			throw new UsernameNotFoundException("Invalid login");
		}
		return user;

	}

	@Transactional(readOnly = true)
	public boolean emailExists(String email) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "User as user where user.email = ?");
		query.setParameter(0, email);

		if (query.list().size() == 0) {
			// no record of this email address
			return false;
		} else {
			return true;
		}
	}
}
