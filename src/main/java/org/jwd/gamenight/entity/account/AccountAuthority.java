package org.jwd.gamenight.entity.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_AUTHORITY")
public class AccountAuthority {

	@Id
	@Column( name = "ACCOUNT_ID" )
	private int account;
	
	@Column( name = "AUTHORITY_ID" )
	private int authority;

	public AccountAuthority() {
	}
	
	public AccountAuthority(int account, int authority) {
		setAccount(account);
		setAuthority(authority);
	}
	
	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getAuthority() {
		return authority;
	}

	public void setAuthority(int authority) {
		this.authority = authority;
	}
}
