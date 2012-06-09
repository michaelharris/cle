package domain;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cle.custom.ActivityItem;
import cle.dao.CommentDao;
import cle.domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
// specifies the Spring configuration to load for this test fixture
@ContextConfiguration(locations = { "file:WebContent/WEB-INF/testing-cle-servlet.xml" })
public class UserActivityStreamTest {
	@Autowired
	CommentDao commentDao;

	@Test
	public void queryToObject() {
		int userId = 1;

		List<ActivityItem> list = commentDao.findCommentActivityByUser(userId);
		for (ActivityItem ai : list) {
			System.out.println(ai.getCommentText());
			System.out.println(ai.getModified());
		}
	}

}
