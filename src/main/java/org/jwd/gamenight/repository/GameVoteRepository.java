package org.jwd.gamenight.repository;

import javax.transaction.Transactional;

import org.jwd.gamenight.entity.game.GameVoteId;
import org.jwd.gamenight.entity.game.GameVotes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface GameVoteRepository  extends PagingAndSortingRepository<GameVotes, Long>
{	
	GameVotes findById(GameVoteId id);
	
	@Query(value =  "Select SUM(VALUE) FROM game_votes WHERE  game_id = :game_id", nativeQuery = true)
	Integer getGameVote(@Param("game_id") int gameId);
	
	@Modifying
	@Query(value = "DELETE FROM game_votes WHERE game_id = :gameId", nativeQuery = true)
	Integer deleteGameVotes(@Param("gameId") int gameId);
}
