package domain;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import cle.dao.HibernateUtil;
import cle.dao.ImageDao;
import cle.dao.ResourceDao;
import cle.domain.Image;
import cle.domain.Resource;

public class ImageTests extends TestCase {

	
	public void testNewImage(){
		
		ResourceDao rDao = new ResourceDao(HibernateUtil.getSessionFactory());
		rDao.setSessionFactory(HibernateUtil.getSessionFactory());
		ImageDao iDao = new ImageDao();
		iDao.setSessionFactory(HibernateUtil.getSessionFactory());
		Resource r1 = rDao.find(1);
		System.out.println("res name " + r1.getTitle());
		
		
		Image i = new Image(r1, "img name", new byte[500]);
	
		Set<Image> img = new HashSet<Image>();
		img.add(i);
		i.setResource(r1);
		r1.setImages(img);
		
		r1.setDescription("newww description");
		rDao.saveOrUpdate(r1);
		iDao.saveOrUpdate(i);
		
		assertNotNull(r1.getImages().contains(i));
		
	}
}
