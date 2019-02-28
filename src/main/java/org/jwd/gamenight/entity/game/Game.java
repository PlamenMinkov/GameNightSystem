package org.jwd.gamenight.entity.game;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "GAME")
public class Game implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GAME_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int gameId;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "image")
	private int image;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "game_id")
	private List<GameVotes> votes;

	@Column(name = "AUTHOR_ID")
	private int authorId;

	public Game()
	{
	}

	public Game(String title, String despcription, int image, int authorId)
	{
		setTitle(title);
		setDescription(despcription);
		setAuthorId(authorId);
		setImage(image);
	}

	public final int getGameId()
	{
		return gameId;
	}

	public final void setGameId(int gameId)
	{
		this.gameId = gameId;
	}

	public final String getTitle()
	{
		return title;
	}

	public final void setTitle(String title)
	{
		this.title = title;
	}

	public final String getDescription()
	{
		return description;
	}

	public final void setDescription(String description)
	{
		this.description = description;
	}

	public final List<GameVotes> getVotes()
	{
		return votes;
	}

	public final void setVotes(List<GameVotes> votes)
	{
		this.votes = votes;
	}

	public final int getAuthorId()
	{
		return authorId;
	}

	public final void setAuthorId(int authorId)
	{
		this.authorId = authorId;
	}
	
	public int getImage()
	{
		return image;
	}

	public void setImage(int image)
	{
		this.image = image;
	}
}
