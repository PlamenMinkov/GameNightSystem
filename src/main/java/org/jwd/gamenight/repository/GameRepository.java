package org.jwd.gamenight.repository;

import java.util.List;

import org.jwd.gamenight.entity.game.Game;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GameRepository  extends PagingAndSortingRepository<Game, Long>
{	
	Game findByTitle(String title);
	
	Game findByGameId(int gameId);
	
	List<Game> findByAuthorId(int authorId);
	
	@Modifying
	@Query("UPDATE Game set title = :title, description = :description, image= :image  WHERE  gameId= :gameId")
	int updateGameFields(@Param("gameId") int gameId, @Param("title") String title, @Param("description") String description, @Param("image") int image);
	
	@Modifying
	@Query("DELETE FROM Game WHERE gameId = :gameId")
	int deleteGame(@Param("gameId") int gameId);
}
