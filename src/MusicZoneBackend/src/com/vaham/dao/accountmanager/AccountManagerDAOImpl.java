package com.vaham.dao.accountmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaham.dao.QueryIDManager;
import com.vaham.dbagent.DBManager;
import com.vaham.dto.Account;
import com.vaham.dto.Address;
/**
 * Data Access Object for managing the Account objects sent 
 * between DBManager and the Web Service.
 */
public class AccountManagerDAOImpl implements AccountManagerDAO
{

	private Logger logger = LogManager.getLogger(AccountManagerDAOImpl.class
			.getName());
	
	public AccountManagerDAOImpl() 
	{
		connectionManager = new DBManager() ;
		connectionManager.init();
	}

	/**
	 * Populates the account object from the database.
	 * @param accountObj object to be populated. It contains only the
	 * username and password which is used for validating the account.
	 * @return true for success and false for failure.
	 */
	public boolean populateAccountObj(Account accountObj)
	{
		String passwordColName = "password" ;
		String firstNameColName = "first_name" ;
		String lastNameColName = "last_name" ;
		String emailColName = "email" ;
		String addressIDColName= "addr_id" ;

		String username = accountObj.getUsername() ; 
		String password = accountObj.getPassword() ; 
		
		logger.debug("UserName : " + username);
		logger.debug("Password : " + password);
		
		if (password == null || password.equals(""))
		{
			logger.error("Error occured, password is null.");
			return false ; 
		}

		List<String> parameterList = new ArrayList<String>() ;

		parameterList.add(username) ;

		List<Map<String,Object>> queryResult = 
				connectionManager.getQueryResult(QueryIDManager.SELECT_ACCOUNT_QRY_USING_USERNAME, 
						parameterList) ;
		
		if ( queryResult.isEmpty() ) 
		{
			logger.error("Account doesnt exist");
			return false ; 
		}

		for (Map.Entry<String, Object> entry : queryResult.get(0).entrySet() ) 
		{
			/* Compare the column name and populate the account object accordingly. */
			if ( entry.getKey().equals(passwordColName) ) 
			{
				String passwordInDB = (String) entry.getValue() ;
				if (! password.equals(passwordInDB) ){
					logger.error("Error occured, password is not right. return false");
					return false ; }
			}
			else if ( entry.getKey().equals(firstNameColName) )
				accountObj.setFirstName( (String) entry.getValue() );

			else if (entry.getKey().equals(lastNameColName))
				accountObj.setLastName( (String) entry.getValue() );

			else if (entry.getKey().equals(emailColName))
				accountObj.setEmail( (String) entry.getValue() );

			else if (entry.getKey().equals(addressIDColName))
			{
				/*
				 *  Fetch the address details from database and
				 *  populate it in the accountObj.addressObj 
				 */
				Long addressID = (Long) entry.getValue() ;

				List<String> tempParameterList = new ArrayList<String>() ; 

				tempParameterList.add( addressID.toString() ) ;

				List < Map < String, Object > > addressDetailsList = 
						connectionManager.getQueryResult(QueryIDManager.SELECT_ADDRESS_QRY_USING_ADDR_ID, 
								tempParameterList) ;

				Address addressObj = createAddressObj(addressID, addressDetailsList.get(0) ) ;

				accountObj.setAddress(addressObj);
			}
		}
		logger.debug("populateAccountObj success");
		return true ; 
	}

	/**
	 * Adds the account to the database.
	 * Checks if the account already exists in the database.
	 * @param accountObj to be added to DB.
	 * @return true for success
	 * 		   false for failure.
	 */
	public boolean postAccount(Account accountObj)
	{
		int status = -1  ; 
		
		String username = accountObj.getUsername() ; 
		
		if ( username.equals("") || accountObj.getPassword().equals("") )
		{
			logger.error("Error occured, username or password is null. return false");
			return false ; 
		}
		
		List<String> parameterList = new ArrayList<String>() ;

		parameterList.add(username) ;

		List<Map<String,Object>> queryResult = 
				connectionManager.getQueryResult(QueryIDManager.SELECT_ACCOUNT_QRY_USING_USERNAME, 
						parameterList) ;
		
		if (! queryResult.isEmpty()) 
		{
			logger.error("Error occured, username already exists. return false");
			return false ; 
		}
		
		connectionManager.startTransaction();
		// Create parameter for inserting address.
		// Address is inserted prior to the account since we need 
		// the address id of the inserted address
		
		parameterList.clear();
		Address accountAddr = accountObj.getAddress() ; 
		
		String parameterValue = accountAddr.getDetails();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountAddr.getProvince();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountAddr.getCountry();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountAddr.getPostalCode();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountAddr.getPhone();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		
		int addressInsertStatus = connectionManager.executeSQL(QueryIDManager.INSERT_ADDRESS_QRY, parameterList) ;
		
		if( addressInsertStatus <= 0 ){
			logger.error("Error occured, Address insert failed. return false");
			return false ; }
		
		parameterList.clear();
		parameterList.add(accountAddr.getPostalCode()) ;
		
		List<Map<String, Object>> addressIDList 
			= connectionManager.getQueryResult(QueryIDManager.SELECT_MAX_ADDR_ID_QRY_USING_POSTAL_CODE, parameterList) ;
		
		if (addressIDList.isEmpty())
		{
			logger.error("Error occured, addressIDList is empty. return false");
			return false ; 
		}
		
		Long addressID = 0L ; 
		for ( Map.Entry<String, Object> entry : addressIDList.get(0).entrySet()) 
		{
			addressID = (Long) entry.getValue() ;
		}
		
		// Create parameter list for adding account
		parameterList.clear();
		
		parameterValue = accountObj.getUsername();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountObj.getPassword();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountObj.getFirstName();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountObj.getLastName();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = accountObj.getEmail();
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		parameterValue = addressID.toString() ;
		parameterList.add(parameterValue != null ? parameterValue : "") ;
		
 		status = connectionManager.executeSQL(QueryIDManager.INSERT_ACCOUNT_QRY, parameterList);
 		connectionManager.endTransaction();
 		logger.debug("status is: "+status);
		return status > 0 ? true : false ; 
	}

	/**
	 * Returns the address for the user.
	 */
	@Override
	public Address getAccountAddress(String username)
	{
		String addressIDColName = "addr_id" ;
		List<String> parameterList = new ArrayList<String>() ;
		
		Address addressObj = null ; 
		
		parameterList.add(username) ;

		List<Map<String,Object>> queryResult = 
				connectionManager.getQueryResult(QueryIDManager.SELECT_ACCOUNT_QRY_USING_USERNAME, 
						parameterList) ;
		
		if ( queryResult.isEmpty() ) 
		{
			logger.error("Account doesnt exist");
			return addressObj; 
		}

		for (Map.Entry<String, Object> entry : queryResult.get(0).entrySet() ) 
		{
			/* Compare the column name and populate the account object accordingly. */
			if (entry.getKey().equals(addressIDColName))
			{
				/*
				 *  Fetch the address details from database and
				 *  populate it in the accountObj.addressObj 
				 */
				Long addressID = (Long) entry.getValue() ;

				List<String> tempParameterList = new ArrayList<String>() ; 

				tempParameterList.add( addressID.toString() ) ;

				List < Map < String, Object > > addressDetailsList = 
						connectionManager.getQueryResult(QueryIDManager.SELECT_ADDRESS_QRY_USING_ADDR_ID, 
								tempParameterList) ;

				addressObj = createAddressObj(addressID, addressDetailsList.get(0) ) ;
			}
		}
		
		return addressObj ;
	}
	
	private DBManager connectionManager ; 

	/**
	 * Creates an address object from the fields 
	 * @param addrID
	 * @param addressResultSet
	 * @return
	 */
	private Address createAddressObj( Long addrID, Map<String,Object> addressResultSet )
	{
		Address addressObj = new Address() ; 
		addressObj.setAddrID(addrID);

		String detailsColName = "details" ;
		String provinceColName = "province" ;
		String countryColName = "country" ;
		String postalCodeColName = "zip" ; 
		String phoneColName = "phone" ;

		for ( Map.Entry<String, Object> entry : addressResultSet.entrySet() )
		{
			if (entry.getKey().equals(detailsColName))
				addressObj.setDetails( (String) entry.getValue() ) ;
			
			else if ( entry.getKey().equals(provinceColName) )
				addressObj.setProvince( (String) entry.getValue() ) ;
			
			else if ( entry.getKey().equals(countryColName) )
				addressObj.setCountry( (String) entry.getValue() ) ;
			
			else if ( entry.getKey().equals(postalCodeColName) )
				addressObj.setPostalCode((String) entry.getValue() ) ;
			
			else if ( entry.getKey().equals(phoneColName) )
				addressObj.setPhone( (String) entry.getValue() ) ;
		}

		return addressObj ;

	}

}
