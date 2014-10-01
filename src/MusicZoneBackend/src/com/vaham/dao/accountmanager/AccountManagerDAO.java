package com.vaham.dao.accountmanager;

import com.vaham.dto.Account;
import com.vaham.dto.Address;
/**
 * DB Agent for managing the Account objects sent 
 * between DBManager and the Web Service.
 */
public interface AccountManagerDAO {

	public boolean populateAccountObj(Account accountObj) ;

	public boolean postAccount(Account accountObj) ;

	public Address getAccountAddress(String userName);
	
}
