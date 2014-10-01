/**
 * 
 */
package com.vaham.dao.orderprocessor;

import com.vaham.dto.PaymentInfo;
import com.vaham.dto.shoppingcart.PurchaseOrder;

public interface OrderProcessorDAO 
{

	/**
	 * Posts the order to the database and updates the purchase order id in the transfer object.
	 * Return true on success; false on failure
	 */
	public boolean postOrder( PurchaseOrder purchaseOrder, PaymentInfo paymentInfo) ;
}
