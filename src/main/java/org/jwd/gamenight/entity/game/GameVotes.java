package org.jwd.gamenight.entity.game;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GAME_VOTES")
public class GameVotes {

	@EmbeddedId 
	GameVoteId id;
	
	@Column( name = "VALUE" )
	private int value;

	public GameVotes() 
	{
	}
	
	public GameVotes(GameVoteId id, int value) 
	{
		setId(id);
		setValue(value);
	}
	
	public int getAccount() 
	{
		return id.accountId;
	}

	public void setId(GameVoteId id) 
	{
		this.id = id;
	}

	public int getGame() 
	{
		return id.gameId;
	}

	public void setGame(int game) 
	{
		this.id.gameId = game;
	}

	public final int getValue() 
	{
		return value;
	}

	public final void setValue(int value) 
	{
		this.value = value;
	}
}
