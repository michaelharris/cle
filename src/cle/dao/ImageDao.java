package cle.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cle.domain.Image;
@Repository("imageDao")
@Transactional
public class ImageDao {
	@Autowired(required = true)
	private SessionFactory sessionFactory;
	private static Log log = LogFactory.getLog(ImageDao.class);

	

	@Autowired(required = true)
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	@Transactional(readOnly = true)
	public Image find(int id) {
		
			return  (Image) sessionFactory.getCurrentSession().get(Image.class,
					id);
		
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public byte[] findByResourceAndName(int resourceId, String fileName){
	
			//This gets all of the images from a resource. - hopefully with caching the will mean fewer db hits.?
			Query query = sessionFactory.getCurrentSession().createQuery(
					"from "	 + "Image as i " + "where i.resource.resourceid = ?" );// + " and i.imagename = '" + fileName + "'");
	query.setParameter(0, resourceId);
	
			List<Image> objects = query.list();
			
			for(Image i : objects){
				if (i.getImagename().equals(fileName)){
				return i.getImagedata();
				}
			}
		return null;
		
		
		
	}
		
	
	public void saveOrUpdate(Image img) {
		
			sessionFactory.getCurrentSession().saveOrUpdate(img);
			
	}

	public void delete(Image img) {
		
			sessionFactory.getCurrentSession().delete(img);
	}
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Image> findAll() {
	
			Query query = sessionFactory.getCurrentSession().createQuery(
					"from " + "Image");
			return query.list();
			
	}
		


	
	
}
