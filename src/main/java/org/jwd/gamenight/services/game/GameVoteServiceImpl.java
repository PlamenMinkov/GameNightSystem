package org.jwd.gamenight.services.game;

import org.jwd.gamenight.entity.game.GameVoteId;
import org.jwd.gamenight.entity.game.GameVotes;
import org.jwd.gamenight.repository.GameVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameVoteServiceImpl implements GameVoteService{	
	@Autowired
	private GameVoteRepository gameVoteRepo;
	
	@Override
	public int setOrUpdateGameVote(int gameId, int accountId, int value) {
		GameVoteId gameVoteId = new GameVoteId(accountId, gameId);
		
		GameVotes vote = gameVoteRepo.findById(gameVoteId);
		
		if(vote == null)
		{
			vote = new GameVotes();
			vote.setId(gameVoteId);
			vote.setValue(value);
			
			gameVoteRepo.save(vote);
			
			return value;
		}
		
		int newValue = vote.getValue() + (value);
		vote.setValue(newValue);
		gameVoteRepo.save(vote);
		
		int res = gameVoteRepo.getGameVote(gameId);
		
		return res;
	}
}
