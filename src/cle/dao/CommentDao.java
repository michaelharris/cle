package cle.dao;

import java.util.Calendar;
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

import cle.domain.Comment;

@Repository
@Transactional
public class CommentDao {
	@Autowired(required = true)
	private SessionFactory sessionFactory;

	private static Log log = LogFactory.getLog(CommentDao.class);

	public CommentDao() {

	}

	public CommentDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(readOnly = true)
	public Comment find(int id) {

		return (Comment) sessionFactory.getCurrentSession().get(Comment.class,
				id);
	}

	public void saveOrUpdate(Comment comment) {
		Calendar calendar = Calendar.getInstance();
		java.util.Date d = calendar.getTime();
		comment.setModified(d);

		sessionFactory.getCurrentSession().saveOrUpdate(comment);

	}

	public void delete(Comment Comment) {
		sessionFactory.getCurrentSession().delete(Comment);

	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Comment> findAll() {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from " + "Comment");
		return query.list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Comment> findSubComments(int commentId) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select comments from Comment as c where c.commentid = ? order by c.created");
		query.setParameter(0, commentId);

		return query.list();

	}

	@Transactional(readOnly = true)
	public List findCommentActivityByUser(int userId) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"select new cle.custom.ActivityItem"
								+ "(c.commenttext, c.modified, c.page.pagenumber, c.resource.resourceid,  c.resource.title)"
								+ " from Comment as c where c.user.userid = ? order by c.modified desc");
		query.setParameter(0, userId);
		return query.list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Comment> findPrivateComments(int userId, int resourceId,
			int pageId) {

		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"from Comment as c "
								+ "where c.resource.resourceid = ? "
								+ "and c.user.userid = ? and c.visibility = '0' and c.page.pagenumber" +
										" = ? order by c.modified desc");
		query.setParameter(0, resourceId);
		query.setParameter(1, userId);
		query.setParameter(2, pageId);

		return query.list();
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<Comment> findPublicComments(int resourceId, int pageId) {

		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Comment as c " + "where c.resource.resourceid = ? "
						+ "and c.visibility = '1' and c.page.pagenumber = ? order by c.modified desc");
		query.setParameter(0, resourceId);

		query.setParameter(1, pageId);

		return query.list();
	}
}
