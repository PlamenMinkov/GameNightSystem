package org.jwd.gamenight.services.game;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import org.jwd.gamenight.entity.game.Game;

public interface GameService {
	List<Game> getAllGames();
	
	Game getGameById(int gameId);
	
	Game getGameData(String title);

	String updateGameFields(int gameId, String title, String description, Part inputFile);
	
	String deleteGame(int gameId);
	
	Map<String, String> createGame(String title, String description, Part image, String username);
	
	List<Game> getGamesByAuthor(String author);
	
	int getVoteOfGame(int gameId);
}
