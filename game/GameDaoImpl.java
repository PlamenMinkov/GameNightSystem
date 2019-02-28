package org.jwd.gamenight.dao.game;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jwd.gamenight.dao.account.AccountDao;
import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.game.Game;
import org.jwd.gamenight.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameDaoImpl implements GameDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private GameVoteDao gameVoteDao;

	@Override
	public List<Game> getGames() {
		// Hibernate Criteria
		Criteria criteria = sessionFactory.openSession().createCriteria(Game.class);

		return criteria.list();
	}

	@Override
	@Transactional
	public boolean addGame(Game game) {
		 Session session = sessionFactory.openSession();
		 Transaction tx = session.beginTransaction();
		
		 session.save(game);
		 tx.commit();
		 session.close();	
		 
		return true;
	}

	@Override
	public Game getGame(String title) {		
		Criteria criteria = sessionFactory.openSession().createCriteria(Game.class);
		criteria.add(Restrictions.like("title", title));
		
		List<Game> games = new ArrayList<Game>();
		games = criteria.list();

		return games != null ? games.get(0) : null;
	}
	
	@Override
	public Game getGameById(int id) {		
		Criteria criteria = sessionFactory.openSession().createCriteria(Game.class);
		criteria.add(Restrictions.eq("gameId", id));
		
		List<Game> games = new ArrayList<Game>();
		games = criteria.list();

		return games != null ? games.get(0) : null;
	}

	@Override
	public int updateGameVoteAndGetNewValue(int gameId, int value) {
		int result, newVoteValue;
		
		Session session = sessionFactory.openSession();
		String hql = "UPDATE Game set vote = vote + ( :value)" + 
	             " WHERE  gameId= :gameId";
		
		Query query = session.createQuery(hql);
		query.setParameter("value", value);
		query.setParameter("gameId", gameId);
		
		result = query.executeUpdate();
		
		Game thisGame = this.getGameById(gameId);
		
		newVoteValue = thisGame.getVote();
		
		session.close();	
		return newVoteValue;
	}

	@Override
	public String updateGameFields(int gameId, String newTitle, String newDescription) {
		int result;
		
		Session session = sessionFactory.openSession();
		String hql = "UPDATE Game set title = :title, description = :description WHERE  gameId= :gameId";
		
		Query query = session.createQuery(hql);
		
		query.setParameter("title", newTitle);
		query.setParameter("description", newDescription);		
		query.setParameter("gameId", gameId);
		
		result = query.executeUpdate();
		session.close();
		
		if(result > 0) {
			return "Успешно обновление!";
		}
		else {
			return "Грешка при убновлението!";
		}
	}

	@Override
	public String deleteGame(int gameId) {
		gameVoteDao.deleteVoteByGameId(gameId);
		
		Session session = sessionFactory.openSession();
		
		String hql = "DELETE FROM Game " + "WHERE gameId = :gameId";
		Query query = session.createQuery(hql);
		query.setParameter("gameId", gameId);
		
		int result = query.executeUpdate();
		
		session.close();

		return null;
	}

	@Override
	public List<Game> getGamesByAuthorId(int authorId) {
		int result;
		
		Session session = sessionFactory.openSession();
		Criteria criteria = sessionFactory.openSession().createCriteria(Game.class);
		criteria.add(Restrictions.eq("authorId", authorId));
		
		List<Game> games = criteria.list();;
		session.close();
		
		return games;
	}
}
