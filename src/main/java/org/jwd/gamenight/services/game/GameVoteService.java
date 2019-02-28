package org.jwd.gamenight.services.game;

import java.util.HashMap;
import java.util.List;

import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.game.Game;

public interface GameVoteService {	
	public int setOrUpdateGameVote(int gameId, int accountId, int value);
}
