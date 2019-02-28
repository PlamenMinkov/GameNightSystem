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
import org.jwd.gamenight.entity.game.Game;
import org.jwd.gamenight.entity.game.GameVotes;
import org.jwd.gamenight.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameVoteDaoImpl implements GameVoteDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private GameDao gameDao;

	@Override
	@Transactional
	public int addGameVote(GameVotes gameVote) {
		int newGameVoteValue;
		int gameId = gameVote.getGame();
		int value = gameVote.getValue();
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.save(gameVote);
		tx.commit();
		 
		newGameVoteValue = gameDao.updateGameVoteAndGetNewValue(gameId, value);
		
		session.close();	
		 
		return newGameVoteValue;
	}
	
	@Override
	public int setOrUpdateGameVote(int gameId, int accountId, int value) {		
		int result, newGameVoteValue;
		
		Session session = sessionFactory.openSession();
		String hql = "FROM GameVotes gv WHERE gv.account= :accountId and gv.game= :gameId";
		Query query = session.createQuery(hql);
		
		query.setParameter("accountId", accountId);
		query.setParameter("gameId", gameId);
		
		
		List votes = query.list();
			
		
		if(votes.isEmpty()) {	
			newGameVoteValue = this.addGameVote(new GameVotes(accountId, gameId, value));
			 
			return newGameVoteValue;
		}
		
		hql = "UPDATE GameVotes set value = value + ( :value)" + 
	             " WHERE account= :accountId and game= :gameId";
		query = session.createQuery(hql);
		
		query.setParameter("value", value);
		query.setParameter("accountId", accountId);
		query.setParameter("gameId", gameId);
		
		result = query.executeUpdate();
		
		newGameVoteValue = gameDao.updateGameVoteAndGetNewValue(gameId, value);
		
		session.close();
		
		return newGameVoteValue;
	}

	@Override
	public boolean deleteVoteByGameId(int gameId) {
		Session session = sessionFactory.openSession();
		
		String hql = "DELETE FROM GameVotes WHERE game = :gameId";
		Query query = session.createQuery(hql);
		query.setParameter("gameId", gameId);
		
		int result = query.executeUpdate();
		
		session.close();
		
		return false;
	}

}
