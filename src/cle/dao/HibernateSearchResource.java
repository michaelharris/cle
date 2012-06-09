package cle.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.spi.SearchFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cle.domain.Resource;

@Repository
@Transactional
public class HibernateSearchResource {
	@Autowired(required = true)
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Resource> resourceSearchByTitleTags(String term) {
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Resource.class).get();
		org.apache.lucene.search.Query query = qb.keyword().wildcard()
				.onField("title").andField("tags").matching(term).createQuery();

		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, Resource.class);

		List<Resource> result = hibQuery.list();
		return result;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Resource> resourceSearchByTitleTagsDescriptionWildcard(String term) {
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Resource.class).get();
	//	org.apache.lucene.search.Query query = qb.keyword().onFields("title", "tags").andField("description").matching(term).createQuery();
		org.apache.lucene.search.Query query = qb.keyword().wildcard().onField("title").andField("tags").andField("description").matching(term).createQuery();
		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, Resource.class);

		List<Resource> result = hibQuery.list();
		return result;

	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> resourceSearchByTitleTagsDescription(String term) {
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Resource.class).get();
	
		org.apache.lucene.search.Query query = qb.keyword().onField("title").boostedTo(3f).andField("tags").boostedTo(2f).andField("description").matching(term).createQuery();
		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, Resource.class);

		List<Resource> result = hibQuery.list();
		return result;

	}
	@SuppressWarnings("unchecked")
	public List<Resource> resourceSearchInPage(String term) {
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());

		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Resource.class).get();
		org.apache.lucene.search.Query query = qb.keyword().wildcard()
				.onField("tags").andField("pages.title").matching(term).createQuery();

		// wrap Lucene query in a org.hibernate.Query
		org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
				query, Resource.class);

		List<Resource> result = hibQuery.list();
		return result;

	}

	public void rebuild() {
		FullTextSession fullTextSession = Search
				.getFullTextSession(sessionFactory.getCurrentSession());
		try {
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			System.out.println("could not rebuild");
			e.printStackTrace();
		}
	}

}
