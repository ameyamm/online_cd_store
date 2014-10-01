/**
 * Class for managing the shopping cart and payment.
 */

package com.vaham.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vaham.shoppingcart.ShoppingCart;
import com.vaham.util.AttributeConstants;
import com.vaham.util.ServletUtils;
import com.vaham.ws.orderprocessor.Account;
import com.vaham.ws.orderprocessor.Address;
import com.vaham.ws.orderprocessor.OrderProcessorWebService;
import com.vaham.ws.orderprocessor.OrderProcessorWebServiceImplService;
import com.vaham.ws.orderprocessor.PaymentInfo;
import com.vaham.ws.orderprocessor.PurchaseOrder;
import com.vaham.ws.productcatalog.Cd;

public class OrderManager {

	/* 
	 * Redirects the action requests to respective functions. 
	 */
	public void processActionRequests(
			String actionPath, 
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException 
	{
		if ( actionPath.equals("/addToCart") ) 
		{
			processAddToCartRequest( request, response );
		}
		else if ( actionPath.equals("/viewCart") )
		{
			processViewCartRequest( request, response ) ;
		}
		else if (actionPath.equals("/removeFromCart") )
		{
			processRemoveFromCartRequest(request, response);
		}
		else if (actionPath.equals("/orderCheckout"))
		{
			processOrderCheckoutRequest(request, response);
		}
		else if (actionPath.equals("/paymentManager"))
		{
			processPaymentRequest(request, response) ;
		}
		else if (actionPath.equals("/orderStatus"))
		{
			processOrderStatus(request, response) ;
		}
	}

	/**
	 * Creates a shopping cart if not exists and adds the CD to the cart.
	 * Stores the shopping cart in the session.
	 */
	private void processAddToCartRequest(
			HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException 
	{
		// check if user is logged in
		String loggedUsername = 
				(String) request.getSession().getAttribute(
						AttributeConstants.LOGGED_IN_USERNAME_ATTRIB);

		if (loggedUsername == null || loggedUsername.equals("") )  
		{
			request.getRequestDispatcher("/Login.jsp").forward(
					request, response);
		}
		else 
		{
			// check if there's already a purchase order object in current
			// session

			ShoppingCart shoppingCart = 
					(ShoppingCart) request.getSession().getAttribute(AttributeConstants.SHOPPING_CART_ATTRIB);

			if (shoppingCart == null) 
			{
				shoppingCart = new ShoppingCart();
			}

			Cd cd = (Cd) request.getSession().getAttribute(AttributeConstants.CD_ATTRIB) ;

			shoppingCart.addCdToCart(cd);

			request.getSession().setAttribute(AttributeConstants.SHOPPING_CART_ATTRIB, shoppingCart);

			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, "CD added to Cart");

			String requestDispatcherString = "/ProductInfo.jsp?" + AttributeConstants.CD_ID_ATTRIB + "=" + cd.getCdId() ; 
			request.getRequestDispatcher(requestDispatcherString).forward(request,response);
		}
	}
	
	/**
	 * Reads the existing shopping cart from the session 
	 * and returns the list of items in shopping cart.
	 */
	private void processViewCartRequest(
			HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException 
	{
		ServletUtils.cleanSessionAttributes(request.getSession());
		
		HttpSession session = request.getSession() ;
		ShoppingCart shoppingCart = (ShoppingCart) 
				session.getAttribute(AttributeConstants.SHOPPING_CART_ATTRIB) ;

		String loggedUser =
				(String) session.getAttribute(AttributeConstants.LOGGED_IN_USERNAME_ATTRIB) ; 
		if ( loggedUser == null || loggedUser.equals("") )
		{
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, "Please Login to view Cart");
		}
		else if (shoppingCart == null || shoppingCart.isEmpty())
		{
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, "No Items in Cart");
		}
		else 
		{
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, "");
			request.getSession().setAttribute(AttributeConstants.SHOPPING_CART_ATTRIB, shoppingCart);
			request.setAttribute(AttributeConstants.SHOPPING_CART_LIST_ATTRIB, shoppingCart.getShoppingCartItems());
		}
		request.getRequestDispatcher("/Mycart.jsp").forward(request, response);
	}
	
	/**
	 * Removes the requested item from the shopping cart 
	 * and loads the updated shopping cart in the session.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processRemoveFromCartRequest(
			HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException 
	{
		ServletUtils.cleanSessionAttributes(request.getSession());

		ShoppingCart shoppingCart = (ShoppingCart) 
				request.getSession().getAttribute(AttributeConstants.SHOPPING_CART_ATTRIB) ;

		shoppingCart.removeCdFromCart((String) request.getParameter(AttributeConstants.CD_ID_ATTRIB));

		if (shoppingCart.isEmpty())
		{
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, "No Items in Cart");
		}
		else 
		{
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, "");
		}
		request.getSession().setAttribute(AttributeConstants.SHOPPING_CART_ATTRIB, shoppingCart);
		request.setAttribute(AttributeConstants.SHOPPING_CART_LIST_ATTRIB, shoppingCart.getShoppingCartItems());
		request.getRequestDispatcher("/Mycart.jsp").forward(request, response);
	}
	
	/**
	 * Calls the createOrder of the webservice and calculates the total price 
	 * with taxes.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processOrderCheckoutRequest(
			HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException
	{
		ServletUtils.cleanSessionAttributes(request.getSession());

		ShoppingCart shoppingCart = (ShoppingCart) 
				request.getSession().getAttribute(AttributeConstants.SHOPPING_CART_ATTRIB) ;

		if (shoppingCart.isEmpty())
		{
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, "No Items in Cart");
		}
		else 
		{
			String username = (String) request.getSession().getAttribute(AttributeConstants.LOGGED_IN_USERNAME_ATTRIB) ;

			String password = (String) request.getSession().getAttribute(AttributeConstants.LOGGED_IN_PASSWORD_ATTRIB) ;
			OrderManager orderMgr = new OrderManager() ; 
			
			PurchaseOrder wsPurchaseOrder = orderMgr.processOrder(username, shoppingCart) ;

			AccountManager accountMgr = new AccountManager() ; 

			Account accountObj = accountMgr.getAccountInfo(username, password) ;

			String errorString = "" ; 
			
			if( accountObj == null )
			{
				errorString = "Invalid Account" ;
			}
			else if( (wsPurchaseOrder != null) && 
					!(wsPurchaseOrder.getErrorString().equals("")) )
			{
				errorString = wsPurchaseOrder.getErrorString() ;
			}
			else 
			{
				HttpSession session = request.getSession() ;
				session.setAttribute(AttributeConstants.SHOPPING_CART_ATTRIB, shoppingCart);
			
				session.setAttribute(AttributeConstants.WS_PURCHASE_ORDER_ATTRIB, wsPurchaseOrder);
				
				request.setAttribute(AttributeConstants.ADDRESS_ATTRIB, accountObj.getAddress() ) ;

				request.setAttribute(AttributeConstants.SHOPPING_CART_LIST_ATTRIB, shoppingCart.getShoppingCartItems());
			}
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, errorString) ;
		}

		request.getRequestDispatcher("/OrderCheckout.jsp").forward(request, response);

	}
	
	/**
	 * Works in two modes :
	 * 1. Processes request from the client for getting the payment information
	 * 	  like payment mode and payment id number. And forwards the request to 
	 * 	  the third party payment manager for authentication.
	 * 2. When clients selects Proceed to payment, it stores the shipping
	 *    address in the purchase order object and redirects the request to 
	 *    the payment page.
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processPaymentRequest(
			HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException 
	{
		ServletUtils.cleanSessionAttributes(request.getSession());
		
		HttpSession session = request.getSession() ;
		
		String mode= request.getParameter(AttributeConstants.MODE_ATTRIB) ;
		
		if ( mode.equals("validatePayment") )
		{
			/* Request from client browser to validate payment*/
			String paymentIdNumber = request.getParameter(AttributeConstants.PAYMENT_ID_ATTRIB) ;
			String paymentType = request.getParameter(AttributeConstants.PAYMENT_TYPE_ATTRIB) ;

			PaymentInfo paymentInfo = new PaymentInfo() ;
			paymentInfo.setPaymentIDNumber(paymentIdNumber);
			paymentInfo.setPaymentType(paymentType);
			session.setAttribute(AttributeConstants.PAYMENT_OBJ_ATTRIB, paymentInfo);
			
			PurchaseOrder wsPurchaseOrder = (PurchaseOrder)
					session.getAttribute(AttributeConstants.WS_PURCHASE_ORDER_ATTRIB) ;
			
			String nameOnCard = request.getParameter(AttributeConstants.PAYMENT_REGISTERED_NAME) ;
			
			/* This is for providing details to third party */
			session.setAttribute(AttributeConstants.PAYMENT_REGISTERED_NAME, nameOnCard);
			session.setAttribute(AttributeConstants.PAYMENT_ID_ATTRIB, paymentIdNumber); 
			session.setAttribute(AttributeConstants.TOTAL_PRICE_ATTRIB, wsPurchaseOrder.getTotalPrice());
			response.sendRedirect(request.getServletContext().getContextPath() + "/paymentHandler");
		}
		else if ( mode.equals("proceedPayment") && !request.getRequestURI().contains("/PaymentHandler")) 
		{	
			/* Request to proceed for payment */
			/* Store shipping address */
			
			String addressDetails = request.getParameter(AttributeConstants.ADDR_DETAILS_ATTRIB) ;
			String addressProvince = request.getParameter(AttributeConstants.ADDR_PROVINCE_ATTRIB) ;
			String addressCountry = request.getParameter(AttributeConstants.ADDR_COUNTRY_ATTRIB) ;
			String addressPostalCode = request.getParameter(AttributeConstants.ADDR_POSTAL_CODE_ATTRIB) ;
			String addressPhone = request.getParameter(AttributeConstants.ADDR_PHONE_ATTRIB) ;

			Address shippingAddress = 
					AccountManager.createAddressObj(addressDetails, 
							addressProvince, 
							addressCountry, 
							addressPostalCode, 
							addressPhone) ;

			PurchaseOrder wsPurchaseOrder = 
					(PurchaseOrder) session.getAttribute(AttributeConstants.WS_PURCHASE_ORDER_ATTRIB) ;
			wsPurchaseOrder.setShippingAddress(shippingAddress);

			/* Following attributes will be required after confirmation of payment */
			session.setAttribute(AttributeConstants.WS_PURCHASE_ORDER_ATTRIB, wsPurchaseOrder);
			
			request.getRequestDispatcher("/Payment.jsp").forward(request, response);
		}
	}
	
	/**
	 * Receives the payment confirmation status from the third party payment handler and
	 * sends the purchase order and payment information object to database to 
	 * load it in database.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processOrderStatus(
			HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException 
	{
		String paymentStatus = (String) request.getSession().getAttribute(AttributeConstants.PAYMENT_STATUS_ATTRIB) ;
		HttpSession session = request.getSession() ;
		String msg = "" ;
		
		PaymentInfo paymentInfo = 
				(PaymentInfo) session.getAttribute(
						AttributeConstants.PAYMENT_OBJ_ATTRIB) ;
		
		PurchaseOrder wsPurchaseOrder = 
				(PurchaseOrder) session.getAttribute(
						AttributeConstants.WS_PURCHASE_ORDER_ATTRIB) ;
		
		if (paymentStatus.toLowerCase().contains("success")) 
		{
			paymentStatus = "ORDERED" ;
			msg = "Payment Successful. " ;
			
			session.removeAttribute(AttributeConstants.WS_PURCHASE_ORDER_ATTRIB);
			session.removeAttribute(AttributeConstants.SHOPPING_CART_ATTRIB);
			session.removeAttribute(AttributeConstants.SHOPPING_CART_LIST_ATTRIB) ;
		}
		else 
		{
			paymentStatus = "DENIED" ;
		}
		
		paymentInfo.setPaymentStatus(paymentStatus);
		
		long purchaseOrderId = confirmOrder(wsPurchaseOrder, paymentInfo) ;
		
		if (purchaseOrderId <= 0 ) 
		{
			msg = "Technical Failure : Error Posting Order. Sorry for inconvenience" ;
		}
		else if ( paymentStatus.equals("DENIED") ) 
		{
			msg = "Payment Authorization Failed. " ;
		}
		else 
		{
			msg += ("Order ID : " + Long.toString(purchaseOrderId) ) ; 
		}
		
		request.setAttribute(AttributeConstants.MESSAGE_ATTRIB, msg);
		session.removeAttribute(AttributeConstants.PAYMENT_OBJ_ATTRIB) ;
		session.removeAttribute(AttributeConstants.PAYMENT_REGISTERED_NAME);
		session.removeAttribute(AttributeConstants.PAYMENT_ID_ATTRIB); 
		session.removeAttribute(AttributeConstants.PAYMENT_STATUS_ATTRIB) ; 
		session.removeAttribute(AttributeConstants.TOTAL_PRICE_ATTRIB);
		
		request.getRequestDispatcher("/OrderStatus.jsp").forward(request,response) ;
	}

	/**
	 * Method for confirming a purchase order.
	 * Calls the OrderProcessor webservice API - confirmOrder which posts
	 * the purchase order and payment information in the database.
	 * @param purchaseOrder to be confirmed
	 * @return true if the order was confirmed and false if not.
	 */
	private long confirmOrder(PurchaseOrder purchaseOrder, PaymentInfo paymentInfo) 
	{
		// Call the client stub of webservice
		OrderProcessorWebServiceImplService orderProcessService = new OrderProcessorWebServiceImplService();
		OrderProcessorWebService orderProcessorStub = orderProcessService
				.getOrderProcessorWebServiceImplPort();
		purchaseOrder = orderProcessorStub.confirmOrder(purchaseOrder, paymentInfo);
		if (!purchaseOrder.getErrorString().equals(""))
			purchaseOrder.setPurchaseOrderID(-1);
		
		return purchaseOrder.getPurchaseOrderID();
	}

	/**
	 * Method for calculating the total price of the shopping cart.
	 * Calculates the total price including the taxes.
	 * @param username
	 * @param shoppingCart
	 * @return
	 */
	private PurchaseOrder processOrder(String username, ShoppingCart shoppingCart) 
	{
		PurchaseOrder wsPurchaseOrder = shoppingCart.getPurchaseOrderObj(username, null) ;
		
		OrderProcessorWebServiceImplService orderProcessService = 
				new OrderProcessorWebServiceImplService();
		OrderProcessorWebService orderProcessorStub = 
				orderProcessService.getOrderProcessorWebServiceImplPort();
		
		wsPurchaseOrder = orderProcessorStub.createOrder(wsPurchaseOrder) ;
		
		return wsPurchaseOrder ;
	}
}
