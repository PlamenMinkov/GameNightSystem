package org.jwd.gamenight.repository;

import javax.transaction.Transactional;

import org.jwd.gamenight.entity.account.AccountAuthority;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Transactional
public interface AccountAuthorityRepository  extends CrudRepository<AccountAuthority, Long>
{	
	@Modifying
	@Query(value = "UPDATE `account_authority` set authority_id = :authorityId  WHERE  account_id= :accountId", nativeQuery = true)
	int updateAccountAuthority(@Param("accountId") int accountId, @Param("authorityId") int authorityId);
}
