package org.jwd.gamenight.dao.game;

import java.util.List;

import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.game.Game;
import org.jwd.gamenight.entity.game.GameVotes;

public interface GameVoteDao {
	int addGameVote(GameVotes gameVote);
	
	int setOrUpdateGameVote(int gameId, int accountId, int value);
	
	boolean deleteVoteByGameId(int id);
}
