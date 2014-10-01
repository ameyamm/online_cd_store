package com.vaham.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet(description = "The Main Servlet acting as the main controller of the project.", urlPatterns = { "/MainServlet" })
public class MainServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() 
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{
		String actionPath = request.getServletPath();

		if ( 
			actionPath.equals("/categories") ||
			actionPath.equals("/products") ||
			actionPath.equals("/productinfo")
		   ) 
		{
			// Product and category processing
			ProductManager productMgr = new ProductManager() ; 
			productMgr.processActionRequests(actionPath, request, response) ;
		}
		else if (
				actionPath.equals("/account") ||
				actionPath.equals("/logout")
				) 
		{
			// Account related processing.
			AccountManager accountMgr = new AccountManager() ; 
			accountMgr.processActionRequests(actionPath, request, response);
		}
		else if (
				actionPath.equals("/orderStatus")
				)
		{
			OrderManager orderMgr = new OrderManager() ;
			orderMgr.processActionRequests(actionPath, request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(
			HttpServletRequest request,
			HttpServletResponse response) 
			throws ServletException, IOException 
	{

		String actionPath = request.getServletPath();


		if (actionPath.equals("/login") ||
			actionPath.equals("/register")) 
		{
			// Account related processing
			AccountManager accountMgr = new AccountManager() ; 
			accountMgr.processActionRequests(actionPath, request, response);
		}
		else if (actionPath.equals("/addToCart") ||
				 actionPath.equals("/viewCart") ||
				 actionPath.equals("/removeFromCart") ||
				 actionPath.equals("/orderCheckout") ||
				 actionPath.equals("/paymentManager") )
		{
			// Order Processing
			OrderManager orderMgr = new OrderManager() ;
			System.err.println("ActionPath: " + actionPath);
			orderMgr.processActionRequests(actionPath, request, response);
		}
	}
}


