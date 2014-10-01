/**
 * Implementation class for the Order Processing.
 */
package com.vaham.ws.orderprocessor;

/* Transfer object library */
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaham.dto.Account;
import com.vaham.dto.PaymentInfo;
import com.vaham.dto.shoppingcart.PurchaseOrder;
import com.vaham.dto.Cd;
import com.vaham.dao.accountmanager.AccountManagerDAO;
import com.vaham.dao.accountmanager.AccountManagerDAOImpl;
import com.vaham.dao.orderprocessor.OrderProcessorDAO;
import com.vaham.dao.orderprocessor.OrderProcessorDAOImpl;
import com.vaham.ws.productcatalog.ProductCatalogWS;
import com.vaham.ws.productcatalog.ProductCatalogWSImpl;

@WebService(endpointInterface = "com.vaham.ws.orderprocessor.OrderProcessorWebService")
public class OrderProcessorWebServiceImpl implements OrderProcessorWebService
{
	private Logger logger = LogManager.getLogger(OrderProcessorWebServiceImpl.class.getName());
	
	/**
	 * Accepts the purchase order from the main controller, 
	 * calculates the total price and returns the purchase order object
	 * @param purchaseOrder containing the cd list in the purchase order
	 * @return purchaseOrder object with the total price
	 * 		   (In case of error, the errorString field contains the 
	 * 			respective error else it is blank.).  
	 *
	 */
	
	@Override
	public PurchaseOrder createOrder ( PurchaseOrder purchaseOrder )
	{
		/* 
		 * 1. Calculate the total price of all the contents.
		 * 2. Store the total price in the Shopping Cart object. 
		 */
		double taxPercent = 10 ;
		
		double totalPrice = 0 ;

		if(purchaseOrder.getCdIdListString() == null ||
				purchaseOrder.getCdIdListString().equals(""))
		{
			logger.debug(this.getClass().toString() + " : Empty CD List ");
			purchaseOrder.setErrorString("Empty cd list");
			return purchaseOrder ;
		}
		
		PurchaseOrder purchaseOrderReturnObj = new PurchaseOrder() ;
		
		purchaseOrderReturnObj.setCdIdListString(purchaseOrder.getCdIdListString());
		purchaseOrderReturnObj.setUsername(purchaseOrder.getUsername()) ;
		
		List<String> cdIdList = purchaseOrderReturnObj.getCdIdList();
		
		ProductCatalogWS productManager = new ProductCatalogWSImpl() ;
		
		Iterator<String> iterator = cdIdList.listIterator() ;
		
		String cdId = null ; 
		
		while(iterator.hasNext())
		{
			cdId = iterator.next() ;
			
			Cd cd = productManager.getProductInfo(cdId);
			
			if(cd.getTitle() == null || cd.getTitle().equals("") ) 
			{
				purchaseOrderReturnObj.setErrorString("Invalid CD Id");
				purchaseOrderReturnObj.setTotalPrice(0);
				return purchaseOrderReturnObj ;
			}
			
			totalPrice += (cd.getPrice()) ;
		}

		
		
		totalPrice = (totalPrice * ( ( 100 + taxPercent ) / 100) ) ; 
		
		BigDecimal formattedPrice = new BigDecimal(totalPrice) ;
		
		totalPrice = formattedPrice.setScale(2, RoundingMode.HALF_UP).doubleValue() ;
		
		purchaseOrderReturnObj.setTotalPrice(totalPrice);
		
		logger.debug("TotalPrice is: " + purchaseOrderReturnObj.getTotalPrice());
		logger.debug("Purchaseorder err string :"+ purchaseOrderReturnObj.getErrorString() + ":" );
		
		return  purchaseOrderReturnObj;
	}
	
	/**
	 * Accepts the shopping cart and payment info. and posts the details
	 * into the database. 
	 * Returns the shopping cart object with the populated order id.
	 * 		   (In case of error, the errorString field contains the 
	 * 			respective error else it is blank.).
	 * @param shoppingCart
	 * @return
	 */
	@Override
	public PurchaseOrder confirmOrder(PurchaseOrder shoppingCart, PaymentInfo paymentInfo)
	{
		OrderProcessorDAO orderProcessorDBAgent = new OrderProcessorDAOImpl(); 
		if (shoppingCart == null || paymentInfo == null)
		{
			shoppingCart = new PurchaseOrder() ;
			shoppingCart.setErrorString("Invalid shopping cart/payment info object");
			return shoppingCart ; 
		}
		
		boolean postStatus = orderProcessorDBAgent.postOrder(shoppingCart, paymentInfo) ;
		
		if ( !postStatus )
		{
			logger.debug("confirm Order is: " + shoppingCart.getErrorString());
		}
		
		return shoppingCart ; 
	}
	
	/**
	 * 1. Reads the username and password from the input account object.
	 * 2. Passes the account object to the DBAgent for getting it populated.
	 * @param account
	 * @return Account obj on success
	 * 		   account obj with blank username and password on failure
	 */
	
	@Override
	public Account getAccount(String username, String password) 
	{
		// Create account parameter list
		AccountManagerDAO accountMgrDBAgent = new AccountManagerDAOImpl(); 
		Account accountObj = new Account(username, password) ;
		boolean status = accountMgrDBAgent.populateAccountObj(accountObj) ;
		if (!status)
		{
			accountObj.setUsername("");
			accountObj.setPassword("");
		}
		return accountObj;
	}
	
	/**
	 * Inserts the account obj in the database.
	 * Returns true on success; false on failure
	 */
	@Override
	public boolean createAccount(Account account)
	{
		AccountManagerDAO accountMgrDBAgent = new AccountManagerDAOImpl() ;
		boolean status = accountMgrDBAgent.postAccount(account) ;
		logger.debug("Status :" + status);
		return status ;
	}
}
