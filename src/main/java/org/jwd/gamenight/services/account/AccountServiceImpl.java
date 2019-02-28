package org.jwd.gamenight.services.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Part;

import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.account.AccountAuthority;
import org.jwd.gamenight.repository.AccountAuthorityRepository;
import org.jwd.gamenight.repository.AccountRepository;
import org.jwd.gamenight.services.WorkOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService
{
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountRepository accountRegster;

	@Autowired
	AccountAuthorityRepository accountAuthorityRepository;

	@Override
	public Iterable<Account> getAccounts(String username)
	{
		String searchName = username == null ? "%" : "%" + username + "%";

		return accountRepository.findByUsernameLike(searchName);
	}

	@Override
	public Account getUserAccountInfo(String accountName)
	{
		return accountRepository.findByUsername(accountName);
	}

	@Override
	public void updateUserAccountInfo(String accountName, Double amount)
	{
		Account updatedAccount = accountRepository.findByUsername(accountName);

		if (updatedAccount != null)
		{
			// updatedAccount.setAccountAmount(amount);
			// accounts.put(accountName, updatedAccount);
		}
	}

	@Override
	public Map<String, String> createUserAccountInfo(String username, String email, String description, String password,
			String firstName, String lastName, Part avatar)
	{
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		int avatarVal = 1;
		String fileValidation = WorkOperations.validateAvatarFile(avatar);
		Account previousUserByUsername = accountRepository.findByUsername(username);
		Account previousUserByEmail = accountRepository.findByEmail(email);
		
		Map<String, String> errorMap = new HashMap<>();        
        
        if (previousUserByUsername == null && previousUserByEmail == null)
        {
            if ("invalid".equals(WorkOperations.validateEmail(email)))
            {
            	errorMap.put("InavlidEmail", "You must enter valid e-mail!");
            }
            if (username.length() < 2 || username.length() > 15)
            {
            	errorMap.put("InavlidEmail", "The count of username symbols must be in interval 2-15!");
            }
            if (firstName.length() < 2 || firstName.length() > 30)
            {
            	errorMap.put("InavlidFirstName", "The count of first name symbols must be in interval 2-30!");
            }
            if (lastName.length() < 2 || lastName.length() > 30)
            {
            	errorMap.put("InavlidLastName", "The count of last name symbols must be in interval 2-30!");
            }
            if (description.length() < 15 || description.length() > 400)
            {
            	errorMap.put("InavlidDescription", "Дължината на представянето трябва с дължина 15-400");
            }
            if (!"valid".equals(fileValidation))
            {
                if ("invalid".equals(fileValidation))
                {
                	errorMap.put("InavlidAvatar", "You can select only image/jpeg or image/png for avatar image!");
                }
                else
                {
                	avatarVal = 0;
                }
            }            
        }
        else if (previousUserByUsername != null)
        {
        	errorMap.put("InavlidUsername", "This username is used!");
        }
        else if (previousUserByEmail != null)
        {
        	errorMap.put("InavlidEmail", "This email is used!");
        }
        
        if(errorMap.size() == 0)
        {
        	Account newAccount = new Account(username, email, description, encoder.encode(password),
    				firstName, lastName, 1);
    		accountRegster.save(newAccount);
    		
    		int newUserId = accountRegster.findByUsername(username).getAccount_id();
    		AccountAuthority accAuthority = new AccountAuthority(newUserId, 0);
    		accountAuthorityRepository.save(accAuthority);
    		
    		if(avatarVal == 1)
    		{
    			try
				{
					WorkOperations.fromPartToFile(avatar, "src/main/resources/static/resources/avatars/" + newUserId + ".jpg", 400, 400);
				} 
    			catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		errorMap.put("success", "success");
        }		
		
		return errorMap;
	}

	@Override
	public int addActivePeriod(int accountId)
	{
		accountRegster.addActivePeriod(accountId);

		Account thisAccount = accountRegster.findByAccountId(accountId);

		return thisAccount.getActivePeriod();
	}

	@Override
	public int changeAuthority(int accountId, int newValue)
	{

		return accountAuthorityRepository.updateAccountAuthority(accountId,
				newValue);
	}

	@Override
	public Map<String, String> editUserAccountInfo(int accountId, String email,
			String firstName, String lastName, Part avatar)
	{
		String fileValidation = WorkOperations.validateAvatarFile(avatar);
		boolean newAvatar = fileValidation.equals("no file is selected") ? false : true;
		Account previousUserByEmail = accountRepository.findByEmail(email);
		int haveAvatar = accountRepository.findByAccountId(accountId).getAvatar();
		
		Map<String, String> errorMap = new HashMap<>();        
        
        if (previousUserByEmail == null || previousUserByEmail.getAccount_id() == accountId)
        {
            if ("invalid".equals(WorkOperations.validateEmail(email)))
            {
            	errorMap.put("InavlidEmail", "You must enter valid e-mail!");
            }
            if (firstName.length() < 2 || firstName.length() > 30)
            {
            	errorMap.put("InavlidFirstName", "The count of first name symbols must be in interval 2-30!");
            }
            if (lastName.length() < 2 || lastName.length() > 30)
            {
            	errorMap.put("InavlidLastName", "The count of last name symbols must be in interval 2-30!");
            }
            if(newAvatar)
            {
            	if (!"valid".equals(fileValidation))
	            {
	                if ("invalid".equals(fileValidation))
	                {
	                	errorMap.put("InavlidAvatar", "You can select only image/jpeg or image/png for avatar image!");
	                }
	            }   
            	else
            	{
            		haveAvatar = 1;
            	}
            }
                    
        }
        else if (previousUserByEmail != null)
        {
        	errorMap.put("InavlidEmail", "This email is used!");
        }
        
        if(errorMap.size() == 0)
        {
        	accountRegster.editProfile(email, haveAvatar, firstName, lastName, accountId);
    		
    		if(newAvatar)
    		{
    			try
				{
					WorkOperations.fromPartToFile(avatar, "src/main/resources/static/resources/avatars/" + accountId + ".jpg", 400, 400);
				} 
    			catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		
    		errorMap.put("success", "success");
        }		
		
		return errorMap;
	}

	@Override
	public Account findAccountByEmail(String email)
	{
		return accountRepository.findByEmail(email);
	}

}
