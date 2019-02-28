package org.jwd.gamenight.services.account;

import java.util.Map;

import javax.servlet.http.Part;

import org.jwd.gamenight.entity.account.Account;

public interface AccountService {
	Iterable<Account> getAccounts(String username);
	
	public Account getUserAccountInfo(String accountName);
	
	public Account findAccountByEmail(String email);

	public void updateUserAccountInfo(String accountName, Double amount);
	
	public Map<String, String> createUserAccountInfo(String username, String email, String description, String password, 
			String firstName, String lastName, Part avatar);
	
	public Map<String, String> editUserAccountInfo(int accountId, String email, String firstName, String lastName, Part avatar);
	
	public int addActivePeriod(int accountId);
	
	int changeAuthority(int accountId, int newValue);
}
