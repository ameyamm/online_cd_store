/**
 * 
 */
package com.vaham.shoppingcart;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaham.ws.orderprocessor.Address;
import com.vaham.ws.orderprocessor.PurchaseOrder;
import com.vaham.ws.productcatalog.Cd;

/**
 * @author Ameya
 *
 */
public class ShoppingCart
{
	private List<Cd> shoppingCartItemList ;
	private long totalPrice ; 
	
	public ShoppingCart() 
	{
		shoppingCartItemList = new ArrayList<Cd>() ;
		totalPrice = 0 ; 
	}
	
	public void addCdToCart(Cd cd)
	{
		shoppingCartItemList.add(cd) ;
		totalPrice += cd.getPrice();
	}

	public void removeCdFromCart(String cdId)
	{
		Iterator<Cd> iterator = 
				shoppingCartItemList.listIterator() ;
		
		Cd temp = null ;
		while (iterator.hasNext())
		{
			temp = iterator.next() ;
			if(temp.getCdId().equals(cdId))
			{
				totalPrice -= temp.getPrice() ;
				iterator.remove(); 
				break ;
			}
				
		}
	}
	
	public boolean isEmpty()
	{
		return shoppingCartItemList.isEmpty() ;
	}

	public PurchaseOrder getPurchaseOrderObj( String username, Address shippingAddress )
	{
		PurchaseOrder wsPurchaseOrder = new PurchaseOrder(); 
		
		wsPurchaseOrder.setErrorString("");
		
		wsPurchaseOrder.setUsername(username);
		
		wsPurchaseOrder.setShippingAddress(shippingAddress);
		
		wsPurchaseOrder.setCdIdListString(getCdIdListString());
		
		return wsPurchaseOrder;
	}
	
	/**
	 * @return the totalPrice
	 */
	public long getTotalPrice() 
	{
		return totalPrice;
	}
	
	public List<Cd> getShoppingCartItems() 
	{
		return shoppingCartItemList ;
	}
	

	private String getCdIdListString()
	{
		String cdIdListString = "" ; 
		Iterator<Cd> iterator = shoppingCartItemList.listIterator() ;
		
		while(iterator.hasNext())
		{
			cdIdListString += ( iterator.next().getCdId() + "|" ) ;
		}
		return cdIdListString ;
	}


}
