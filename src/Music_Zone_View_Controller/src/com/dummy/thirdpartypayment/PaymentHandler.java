package com.dummy.thirdpartypayment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vaham.util.AttributeConstants;
/**
 * Servlet implementation class PaymentHandler
 */
@WebServlet(description = "A dummy third party Payment System", urlPatterns = { "/PaymentHandler" })
public class PaymentHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static int requestNo = 0 ; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentHandler() 
    {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String actionPath = request.getServletPath();
		
		if (actionPath.equals("/paymentHandler"))
		{
			request.getRequestDispatcher("/ThirdPartyPayment.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String actionPath = request.getServletPath();
		
		if( actionPath.equals("/authPayment"))
		{
			requestNo++;
			
			HttpSession session = request.getSession() ;
			if( requestNo == 5 )
			{
				session.setAttribute(
						AttributeConstants.PAYMENT_STATUS_ATTRIB, "failure") ;
				requestNo = 0 ; 
			}
			else {
				session.setAttribute(
					AttributeConstants.PAYMENT_STATUS_ATTRIB, "success");
			}
			
			response.sendRedirect(getServletContext().getContextPath() + "/orderStatus");
		}
	}

}
