package org.jwd.gamenight.repository;

import javax.transaction.Transactional;

import org.jwd.gamenight.entity.account.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface AccountRepository  extends CrudRepository<Account, Long>
{
	public Account findByUsernameAndPassword(String username, String password);
	
	public Account findByUsername(String username);
	
	public Account findByEmail(String email);
	
	public Account findByAccountId(int accountId);
	
	@Query(value = "SELECT * FROM `account` WHERE username like :username", nativeQuery = true)
	public Iterable<Account> findByUsernameLike(@Param("username") String username);
	
	@Modifying
	@Query("UPDATE Account set activePeriod = activePeriod + 1  WHERE  accountId= :accountId")
	int addActivePeriod(@Param("accountId") int accountId);
	
	@Modifying
	@Query("UPDATE Account SET email= :email, avatar= :avatar,firstName= :firstName, lastName= :lastName WHERE   accountId= :accountId")
	int editProfile(@Param("email") String email, @Param("avatar") int avatar, @Param("firstName") String firstName, 
			@Param("lastName") String lastName, @Param("accountId") int accountId);
}
