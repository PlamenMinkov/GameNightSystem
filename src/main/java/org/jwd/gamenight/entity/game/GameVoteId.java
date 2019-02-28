package org.jwd.gamenight.entity.game;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GameVoteId implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7699844872528845207L;

	@Column(name="account_id")
	int accountId;
	
	@Column(name="game_id")
	int gameId;
	
	public GameVoteId()
	{
	}
	
	public GameVoteId(int authorId, int gameId)
	{
		this.accountId = authorId;
		this.gameId = gameId;
	}
	
	public boolean equals(Object obj) 
	{
		return (this == obj);
	}
	
	public int hashCode()
	{
		return accountId;
	}
}
