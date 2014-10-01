package com.vaham.dao.orderprocessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaham.dao.QueryIDManager;
import com.vaham.dao.accountmanager.AccountManagerDAO;
import com.vaham.dao.accountmanager.AccountManagerDAOImpl;
import com.vaham.dbagent.DBManager;
import com.vaham.dto.Address;
import com.vaham.dto.PaymentInfo;
import com.vaham.dto.shoppingcart.PurchaseOrder;
/**
 * Implementation class for OrderProcessing.
 *
 */
public class OrderProcessorDAOImpl implements OrderProcessorDAO
{

	private DBManager connectionManager ;
	private Logger logger = 
			LogManager.getLogger(OrderProcessorDAOImpl.class.getName());
	
	public OrderProcessorDAOImpl()
	{
		connectionManager = new DBManager() ;
		connectionManager.init();
	}
	
	/**
	 * Posts the order to the database and updates the purchase order id in the purchaseorder object.
	 * Return true for success 
	 *		  false for failure
	 */
	public boolean postOrder( PurchaseOrder purchaseOrder, PaymentInfo paymentInfo)
	{
		connectionManager.startTransaction();

		boolean status = postAndGetPurchaseOrder(purchaseOrder, paymentInfo) ;

		if( !status )
			return false ; 

		/* Sets the purchase order id generated in the purchase order structure */
		purchaseOrder.setPurchaseOrderID(purchaseOrder.getPurchaseOrderID()) ;

		boolean returnStatus = postPurchaseOrderItems(purchaseOrder) ;

		connectionManager.endTransaction();

		logger.debug("PostOrder Status is:" + returnStatus);
		
		return returnStatus  ; 
	}
	
	/**
	 * Insert the new address in the database and 
	 * fetches and returns the address id of the inserted address.
	 * @param purchaseOrder
	 * @return addressID of the inserted address.
	 */
	private Long insertAndGetAddressIDForNewAddress( PurchaseOrder purchaseOrder )
	{
		Long addressID = -1L ;
		/* Insert the new address into the database */
		List<String> addressParameterList = new ArrayList<String>() ;
		addressParameterList.add( purchaseOrder.getShippingAddress().getDetails() ) ;
		addressParameterList.add( purchaseOrder.getShippingAddress().getProvince() ) ;
		addressParameterList.add( purchaseOrder.getShippingAddress().getCountry() ) ;
		addressParameterList.add( purchaseOrder.getShippingAddress().getPostalCode() ) ;
		addressParameterList.add( purchaseOrder.getShippingAddress().getPhone() ) ;
		
		int rowsAffected = connectionManager.executeSQL(QueryIDManager.INSERT_ADDRESS_QRY, addressParameterList) ;
		
		if (rowsAffected <= 0) 
		{
			return -1L  ; 
		}

		List<String> tempList = new ArrayList<String>() ;
		tempList.add(purchaseOrder.getShippingAddress().getPostalCode()) ; 
		/* Fetch the address ID of the newly added address */

		List<Map<String, Object>> addressIDList = 
				connectionManager.getQueryResult(
						QueryIDManager.SELECT_MAX_ADDR_ID_QRY_USING_POSTAL_CODE, 
						tempList) ;

		for (Map.Entry<String, Object> entry : addressIDList.get(0).entrySet()) 
		{
			addressID = (Long) entry.getValue() ; // String is returned as a part of addressID.
		}
		logger.debug("addressID is: " + addressID);
		return addressID; 
	}

	/**
	 * Inserts the purchase order in the database (PO table) and returns the ID for the inserted record.
	 * @param purchaseOrder
	 * @param paymentInfo
	 * @return purchaseOrderID of the inserted purchaseOrder.
	 */
	private boolean postAndGetPurchaseOrder( PurchaseOrder purchaseOrder, PaymentInfo paymentInfo )
	{
		List<String> parameterList = new ArrayList<String>() ;
		
		Long purchaseOrderID = -1L ;

		int rowsAffected = 0 ;

		if(purchaseOrder.getUsername() == null || 
				purchaseOrder.getUsername().equals(""))
		{
			purchaseOrder.setErrorString("Missing username") ;
			return false; 
		}
		// Create purchase order insert parameter list.

		parameterList.add(purchaseOrder.getUsername()) ;
		
		parameterList.add(paymentInfo.getPaymentStatus()) ; 

		parameterList.add(paymentInfo.getPaymentIDNumber()) ;
		
		parameterList.add(paymentInfo.getPaymentType()) ;

		/* 
		 * If ( the address in the purchase order matches the address of the client (Billing address))
		 * 		then simple use that address for inserting this purchase order.
		 * Else 
		 * 		Add the new shipping address to the database.
		 * (Future work: Check if the previous addresses in the Purchase order records for the client 
		 * 		matches this new shipping address. ) 
		 */
		Long addressId = -1L ; 
		
		AccountManagerDAO accountMgr = new AccountManagerDAOImpl() ;
		Address billingAddress = accountMgr.getAccountAddress(purchaseOrder.getUsername()) ;
		
		if (purchaseOrder.getShippingAddress() == null )
		{
			purchaseOrder.setErrorString("Missing Shipping Address.");
			
		}
		
		if ( purchaseOrder.getShippingAddress().equals(billingAddress) ) 
		{
			addressId = billingAddress.getAddrID() ; 
		}
		else 
		{	
			addressId = insertAndGetAddressIDForNewAddress(purchaseOrder) ;
		}
		
		if (addressId == -1)
		{
			purchaseOrder.setErrorString("Error posting address to DB");
			return false ; 
		}
			

		parameterList.add(addressId.toString());

		rowsAffected = connectionManager.executeSQL(QueryIDManager.INSERT_PURCHASE_ORDER_QRY, parameterList) ;
		
		if (rowsAffected <= 0) 
		{
			purchaseOrder.setErrorString("Error posting purchase order");
			return false; 
		}

		List<String> tempList = new ArrayList<String>() ;
		tempList.add(purchaseOrder.getUsername()) ; 

		List<Map<String, Object>> recentPurchaseOrderIDList = 
				connectionManager.getQueryResult(QueryIDManager.SELECT_MAX_PURCHASE_ORDER_ID_QRY_USING_USERNAME, 
						tempList) ;

		for (Map.Entry<String, Object> entry : recentPurchaseOrderIDList.get(0).entrySet()) 
		{
			purchaseOrderID = (Long) entry.getValue() ; // String is returned as a part of addressID.
		}
		
		logger.debug("purchaseOrderID is: "+purchaseOrderID);
		
		purchaseOrder.setPurchaseOrderID(purchaseOrderID.longValue());
		
		return true ;
	}

	/**
	 * Inserts the items in the purchase order to the POItem table.
	 * @param purchaseOrder
	 * @return true for success 
	 *		  false for failure
	 */
	private boolean postPurchaseOrderItems( PurchaseOrder purchaseOrder )
	{
		List<String> parameterList = new ArrayList<String>() ;
		
		if(purchaseOrder.getCdIdListString() == null || 
				purchaseOrder.getCdIdListString().equals("") )
		{
			purchaseOrder.setErrorString("Empty CD List");
			return false ; 
		}
		
		List<String> cdList = purchaseOrder.getCdIdList(); 
		Iterator<String> iterator = cdList.listIterator() ; 
		
		while(iterator.hasNext()) 
		{
			parameterList.add( Long.toString(purchaseOrder.getPurchaseOrderID()) );
			parameterList.add( iterator.next() ) ;
			int rowsAffected = connectionManager.executeSQL(QueryIDManager.INSERT_PURCHASE_ORDER_ITEM_QRY, parameterList) ;
			if (rowsAffected <= 0) {
				String error = "Error occured posting purchase order items failed." ; 
				logger.error(error);
				purchaseOrder.setErrorString(error);
				return false ; 
			}
			parameterList.clear(); 
		}
		logger.debug("postPurchaseOrderItems success. return true");
		
		return true ; 
	}

}
