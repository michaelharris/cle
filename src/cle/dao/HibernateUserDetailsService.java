package cle.dao;

import java.util.List;

import cle.domain.*;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class HibernateUserDetailsService implements UserDetailsService {

	@Autowired
	UserDao uDao;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {

		UserDetails foundUser = (UserDetails) uDao.findByEmail(username);

		foundUser.getAuthorities().size();// ensure authorities are loaded

		return foundUser;

	}

}
