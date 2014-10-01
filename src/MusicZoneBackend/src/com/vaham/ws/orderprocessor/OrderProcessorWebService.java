/**
 * 
 */
package com.vaham.ws.orderprocessor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.vaham.dto.Account;
import com.vaham.dto.PaymentInfo;
import com.vaham.dto.shoppingcart.PurchaseOrder;

/**
 *	Interface for OrderProcessorWebService. 
 *
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface OrderProcessorWebService 
{
	@WebMethod public Account getAccount(@WebParam(name = "username")String username, @WebParam(name = "password")String password) ;
	@WebMethod public boolean createAccount(@WebParam(name = "account")Account account);
	@WebMethod public PurchaseOrder createOrder ( @WebParam(name = "purchaseOrder")PurchaseOrder purchaseOrder) ;
	@WebMethod public PurchaseOrder confirmOrder(@WebParam(name = "shoppingCart")PurchaseOrder shoppingCart, @WebParam(name = "paymentInfo")PaymentInfo paymentInfo) ;
}
