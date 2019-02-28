package org.jwd.gamenight.dao.account;

import java.util.List;

import org.jwd.gamenight.entity.account.Account;

public interface AccountDao {
	Account getAccount(String username);
	
	Account getAccountById(int id);
	
	List<Account> getAccounts(String username);

	boolean addAccount(Account account);
	
	int addActivePeriod(int accountId);
	
	Boolean changeAuthority(int accountId, int newValue);
}
