package org.jwd.gamenight.entity.account;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.jwd.gamenight.entity.game.GameVotes;

@Entity
@Table(name = "account")
public class Account implements Serializable
{
	private static final long serialVersionUID = 1L;

	private static String lastCustumer = null;

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int accountId;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;
	
	@Column(name = "description")
	private String description;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "password")
	private String password;

	@Column(name = "active_period")
	private int activePeriod;

	@Column(name = "avatar")
	private int avatar;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "account_authority", joinColumns =
	{ @JoinColumn(name = "account_id", referencedColumnName = "account_id") }, inverseJoinColumns =
	{ @JoinColumn(name = "authority_id", referencedColumnName = "authority_id") })
	private List<Authority> authorities;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "account_id")
	private List<GameVotes> votes;

	public Account()
	{
	}

	public Account(String username, String email, String description, String password, String firstName,
			String lastName, int avatar)
	{
		// setAccount_id(account_id);
		setUsername(username);
		setEmail(email);
		setPassword(password);
		setDescription(description);
		setFirstName(firstName);
		setLastName(lastName);
		setAvatar(avatar);
		setActivePeriod(0);
	}

	public static final String getLastCustumer()
	{
		return lastCustumer;
	}

	public static final void setLastCustumer(String lastCustumer)
	{
		Account.lastCustumer = lastCustumer;
	}

	public final int getAccount_id()
	{
		return accountId;
	}

	public final void setAccount_id(int account_id)
	{
		this.accountId = account_id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public final String getUsername()
	{
		return username;
	}

	public final void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public final String getFirstName()
	{
		return firstName;
	}

	public final void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public final String getLastName()
	{
		return lastName;
	}

	public final void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public final String getPassword()
	{
		return password;
	}

	public final void setPassword(String password)
	{
		this.password = password;
	}

	public final int getActivePeriod()
	{
		return activePeriod;
	}

	public final void setActivePeriod(int activePeriod)
	{
		this.activePeriod = activePeriod;
	}

	public final int getAvatar()
	{
		return avatar;
	}

	public final void setAvatar(int avatar)
	{
		this.avatar = avatar;
	}

	public List<Authority> getAuthorities()
	{
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities)
	{
		this.authorities = authorities;
	}
}
