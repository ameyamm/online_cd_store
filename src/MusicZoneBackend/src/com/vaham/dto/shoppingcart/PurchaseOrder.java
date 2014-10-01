/**
 * 
 */
package com.vaham.dto.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.vaham.dto.Address;

/**
 * Purchase Order class representing an object of a Shopping Cart order.
 * The cd Id list is stored as a list of strings
 * 
 */
public class PurchaseOrder implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	public static final char delimiter = '|';
	
	private long purchaseOrderID; 
	
	private String username ;
	
	private String cdIdListString;
	
	private Address shippingAddress;
	
	private double totalPrice;
	
	private String errorString ;

	/**
	 * Default Constructor
	 */
	
	public PurchaseOrder() 
	{
		errorString = "" ; 
	}

	/**
	 * @return the purchaseOrderID
	 */
	public long getPurchaseOrderID() 
	{
		return purchaseOrderID;
	}

	/**
	 * @param purchaseOrderID the purchaseOrderID to set
	 */
	public void setPurchaseOrderID(long purchaseOrderID) 
	{
		this.purchaseOrderID = purchaseOrderID;
	}

	

	/**
	 * @return the Username
	 */
	public String getUsername() 
	{
		return username;
	}

	/**
	 * @return the shippingAddress
	 */
	public Address getShippingAddress() 
	{
		return shippingAddress;
	}

	/**
	 * @return the cdIdListString
	 */
	public String getCdIdListString() {
		return cdIdListString;
	}

	/**
	 * @param cdIdListString the cdIdListString to set
	 */
	public void setCdIdListString(String cdIdListString) {
		this.cdIdListString = cdIdListString;
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() 
	{
		return totalPrice;
	}

	/**
	 * @param usernamet
	 *            the username to set
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}

	/**
	 * @param shippingAddress
	 *            the shippingAddress to set
	 */
	public void setShippingAddress(Address shippingAddress) 
	{
		this.shippingAddress = shippingAddress;
	}

	/**
	 * @param totalPrice
	 *            the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) 
	{
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the errorString
	 */
	public String getErrorString() {
		return errorString;
	}

	/**
	 * @param errorString the errorString to set
	 */
	public void setErrorString(String errorString) 
	{
		this.errorString = errorString;
	}
	
	public List<String> getCdIdList()
	{
		List<String> cdIdList = new ArrayList<String>() ;
		StringTokenizer tokenizer = 
				new StringTokenizer(cdIdListString, Character.toString(PurchaseOrder.delimiter)) ;
		
		while(tokenizer.hasMoreTokens())
		{
			cdIdList.add(tokenizer.nextToken()) ;
		}
		return cdIdList ;
	}
}
