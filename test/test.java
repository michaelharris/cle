import org.hibernate.classic.Session;

import cle.dao.HibernateUtil;
import cle.dao.RoleDao;
import cle.dao.UserDao;
import cle.domain.Role;
import cle.domain.User;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		User u;
		Role r;
		UserDao uDao;
		RoleDao rDao = new RoleDao(HibernateUtil.getSessionFactory());
		uDao = new UserDao(HibernateUtil.getSessionFactory());
		//rDao = new RoleDao();
		
		//Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		uDao.setSessionFactory(HibernateUtil.getSessionFactory());
		rDao.setSessionFactory(HibernateUtil.getSessionFactory());
		//s.getTransaction().begin();
	
		//s.getTransaction().commit();
	//	u = (User) s.get(User.class,1);
	
		//System.out.println(u.getEmail());
		
r = new Role("Other User2", null);
		
		rDao.saveOrUpdate(r);
		//r = new Role("Other User", null);
		
		//rDao.saveOrUpdate(r);
		
	}

}
