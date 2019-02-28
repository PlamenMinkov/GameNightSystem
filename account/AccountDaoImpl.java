package org.jwd.gamenight.dao.account;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jwd.gamenight.entity.account.Account;
import org.jwd.gamenight.entity.account.AccountAuthority;
import org.jwd.gamenight.entity.account.Authority;
import org.jwd.gamenight.entity.game.Game;
import org.jwd.gamenight.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountDaoImpl implements AccountDao {

	 //@PersistenceContext
	 //private EntityManager entityManager;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Account> getAccounts(String username) {
		// Hibernate Criteria
		Criteria criteria = sessionFactory.openSession().createCriteria(Account.class);
		
		if(username != null) {
			criteria.add(Restrictions.like("username", "%" + username + "%"));
		}

		return criteria.list();
	}

	@Override
	@Transactional
	public boolean addAccount(Account account) {
		 Session session = sessionFactory.openSession();
		 Transaction tx = session.beginTransaction();
		System.out.println(account.getFirstName());
		 session.save(account);
		 tx.commit();
		 
		 AccountAuthority accAuthority = new AccountAuthority(0, 1);
		 
		 tx = session.beginTransaction();
		
		 session.save(accAuthority);
		 tx.commit();
		 session.close();
		 
		 

		return true;
	}

	@Override
	public Account getAccount(String username) {
		Criteria criteria = sessionFactory.openSession().createCriteria(Account.class);
		criteria.add(Restrictions.like("username", username));
		
		List<Account> accounts = criteria.list();

		return accounts != null ? accounts.get(0) : null;
	}

	@Override
	public int addActivePeriod(int accountId) {
		int result, newActivePeriod;
		
		Session session = sessionFactory.openSession();
		String hql = "UPDATE Account set activePeriod = activePeriod + 1 WHERE  account_id= :accountId";
		Query query = session.createQuery(hql);
		query.setParameter("accountId", accountId);
		
		result = query.executeUpdate();
		session.close();
		
		Account thisAccount = this.getAccountById(accountId);
		
		newActivePeriod = thisAccount.getActivePeriod();
		
		return newActivePeriod;
	}

	@Override
	public Account getAccountById(int id) {
		Criteria criteria = sessionFactory.openSession().createCriteria(Account.class);
		criteria.add(Restrictions.eq("account_id", id));
		
		List<Account> accounts = criteria.list();

		return accounts != null ? accounts.get(0) : null;
	}

	@Override
	public Boolean changeAuthority(int accountId, int newValue) {
		int result;
		
		Session session = sessionFactory.openSession();
		String hql = "UPDATE AccountAuthority set authority = :newValue WHERE account= :accountId";
		Query query = session.createQuery(hql);
		query.setParameter("newValue", newValue);
		query.setParameter("accountId", accountId);		
		
		result = query.executeUpdate();
		session.close();
		
		return true;
	}

}
