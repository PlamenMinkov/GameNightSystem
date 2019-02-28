package org.jwd.gamenight.dao.game;

import java.util.List;

import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.game.Game;

public interface GameDao {
	Game getGame(String title);
	
	List<Game> getGames();

	boolean addGame(Game game);
	
	Game getGameById(int id);
	
	int updateGameVoteAndGetNewValue(int gameId, int value);
	
	String updateGameFields(int gameId, String newTitle, String newDescription);
	
	String deleteGame(int gameId);
	
	List<Game> getGamesByAuthorId(int authorId);
}
