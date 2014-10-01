package com.vaham.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaham.util.AttributeConstants;
import com.vaham.util.ServletUtils;
import com.vaham.ws.orderprocessor.OrderProcessorWebService;
import com.vaham.ws.orderprocessor.OrderProcessorWebServiceImplService;
import com.vaham.ws.orderprocessor.Account;
import com.vaham.ws.orderprocessor.Address;

/**
 * Account manager class for controlling and managing the requests related to
 * the accounts.
 * 
 */
public class AccountManager {

	/**
	 * Method that will process the all the requests coming from Main Servlet.
	 * 
	 * @param actionPath
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void processActionRequests(String actionPath,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (actionPath.equals("/account")) {
			processViewAccount(request, response);
		} else if (actionPath.equals("/logout")) {
			processLogoutRequest(request, response);
		} else if (actionPath.equals("/login")) {
			processLoginRequest(request, response);
		} else if (actionPath.equals("/register")) {
			processAccountRegistration(request, response);
		}
	}

	/**
	 * Method for displaying all the related information of the currently logged
	 * account.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processViewAccount(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ServletUtils.cleanSessionAttributes(request.getSession());
		String username = (String) request.getSession().getAttribute(
				AttributeConstants.LOGGED_IN_USERNAME_ATTRIB);
		String password = (String) request.getSession().getAttribute(
				AttributeConstants.LOGGED_IN_PASSWORD_ATTRIB);
		
		Account accountObj = getAccountInfo(username, password);

		request.setAttribute(AttributeConstants.ACCOUNT_FIRST_NAME_ATTRIB,
				accountObj.getFirstName());
		request.setAttribute(AttributeConstants.ACCOUNT_LAST_NAME_ATTRIB,
				accountObj.getLastName());
		request.setAttribute(AttributeConstants.ACCOUNT_EMAIL_ATTRIB,
				accountObj.getEmail());
		request.setAttribute(AttributeConstants.ACCOUNT_USERNAME_ATTRIB,
				accountObj.getUsername());
		request.setAttribute(AttributeConstants.ADDR_COUNTRY_ATTRIB, accountObj
				.getAddress().getCountry());
		request.setAttribute(AttributeConstants.ADDR_PHONE_ATTRIB, accountObj
				.getAddress().getPhone());
		request.setAttribute(AttributeConstants.ADDR_POSTAL_CODE_ATTRIB,
				accountObj.getAddress().getPostalCode());
		request.setAttribute(AttributeConstants.ADDR_PROVINCE_ATTRIB,
				accountObj.getAddress().getProvince());
		request.setAttribute(AttributeConstants.ADDR_DETAILS_ATTRIB, accountObj
				.getAddress().getDetails());

		request.getRequestDispatcher("/Account.jsp").forward(request, response);
	}

	/**
	 * Method used for logging out and invalidating the current session.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processLogoutRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();

		response.sendRedirect(request.getContextPath() + "/Logout.jsp");
	}

	/**
	 * Method used for processing and managing users logging.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processLoginRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletUtils.cleanSessionAttributes(request.getSession());
		String username, password;
		username = request.getParameter("username");
		password = request.getParameter("password");

		boolean validationSucceeded = validateUser(username, password);

		// Managing if login was successful or not
		if (validationSucceeded) {

			request.getSession().setAttribute(
					AttributeConstants.LOGGED_IN_USERNAME_ATTRIB, username);

			request.getSession().setAttribute(
					AttributeConstants.LOGGED_IN_PASSWORD_ATTRIB, password);

			String lastVisitedPage = ((String) request.getSession()
					.getAttribute(AttributeConstants.LAST_VISITED_PATH_ATTRIB)) == null ? (String) request
					.getContextPath() + "/Home.jsp"
					: (String) request.getSession().getAttribute(
							AttributeConstants.LAST_VISITED_PATH_ATTRIB);
			System.err.println("Last visited page: " + lastVisitedPage);

			response.sendRedirect(lastVisitedPage);
		} else {
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB,
					AttributeConstants.MESSAGE_LOGIN_UNSUCCESSFULL);
			request.getRequestDispatcher("/Login.jsp").forward(request,
					response);
		}
	}

	/**
	 * Method used for processing users registration.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processAccountRegistration(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletUtils.cleanSessionAttributes(request.getSession());
		String details, province, country, postalCode, phone;

		details = request.getParameter(AttributeConstants.ADDR_DETAILS_ATTRIB);
		province = request
				.getParameter(AttributeConstants.ADDR_PROVINCE_ATTRIB);
		country = request.getParameter(AttributeConstants.ADDR_COUNTRY_ATTRIB);
		postalCode = request
				.getParameter(AttributeConstants.ADDR_POSTAL_CODE_ATTRIB);
		phone = request.getParameter(AttributeConstants.ADDR_PHONE_ATTRIB);

		/* Create Address obj. */
		Address address = createAddressObj(details, province, country,
				postalCode, phone);

		String username, password, firstName, lastName, email;

		firstName = request
				.getParameter(AttributeConstants.ACCOUNT_FIRST_NAME_ATTRIB);
		lastName = request
				.getParameter(AttributeConstants.ACCOUNT_LAST_NAME_ATTRIB);
		username = request
				.getParameter(AttributeConstants.ACCOUNT_USERNAME_ATTRIB);
		password = request
				.getParameter(AttributeConstants.ACCOUNT_PASSWORD_ATTRIB);
		email = request.getParameter(AttributeConstants.ACCOUNT_EMAIL_ATTRIB);

		boolean registrationSucceeded = register(username, password, firstName,
				lastName, email, address);

		// handling registration success and failure
		if (registrationSucceeded) {
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB,
					AttributeConstants.MESSAGE_REGISTER_SUCCESSFULL);
			request.getRequestDispatcher("/Login.jsp").forward(request,
					response);
		} else {
			request.setAttribute(AttributeConstants.MESSAGE_ATTRIB,
					AttributeConstants.MESSAGE_REGISTER_UNSUCCESSFULL);

			// displaying previously typed account details
			request.setAttribute(AttributeConstants.ACCOUNT_FIRST_NAME_ATTRIB,
					firstName);
			request.setAttribute(AttributeConstants.ACCOUNT_LAST_NAME_ATTRIB,
					lastName);
			request.setAttribute(AttributeConstants.ACCOUNT_EMAIL_ATTRIB, email);
			request.setAttribute(AttributeConstants.ADDR_PHONE_ATTRIB, phone);
			request.setAttribute(AttributeConstants.ADDR_DETAILS_ATTRIB,
					details);
			request.setAttribute(AttributeConstants.ADDR_PROVINCE_ATTRIB,
					province);
			request.setAttribute(AttributeConstants.ADDR_COUNTRY_ATTRIB,
					country);
			request.setAttribute(AttributeConstants.ADDR_POSTAL_CODE_ATTRIB,
					postalCode);

			request.getRequestDispatcher("/Reg.jsp").forward(request, response);
		}
	}

	/**
	 * Method for checking validity of user.
	 * 
	 * @param username
	 * @param password
	 * @return true if user is valid
	 */
	private boolean validateUser(String username, String password) {
		return getAccountInfo(username, password) != null ? true : false;
	}

	/**
	 * Class interface to check the validity of the entered username and
	 * password.
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	Account getAccountInfo(String username, String password) {
		// Call the client stub of webservice.
		OrderProcessorWebServiceImplService orderProcessService = new OrderProcessorWebServiceImplService();
		OrderProcessorWebService orderProcessorStub = orderProcessService
				.getOrderProcessorWebServiceImplPort();

		Account accountObj = orderProcessorStub.getAccount(username, password);

		return accountObj.getUsername().equals("") ? null : accountObj;
	}

	/**
	 * Class interface for registering a user with all details. It will use the
	 * OrderProcessorWebService client.
	 * 
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @return true if account was successfully created and false if not.
	 */
	private boolean register(String username, String password,
			String firstName, String lastName, String email, Address address) {

		boolean registrationSuccess = false;
		// Call the client stub web service.
		OrderProcessorWebServiceImplService orderProcessService = new OrderProcessorWebServiceImplService();
		OrderProcessorWebService orderProcessorStub = orderProcessService
				.getOrderProcessorWebServiceImplPort();

		/* Create account object */
		Account accountObj = new Account();
		accountObj.setUsername(username);
		accountObj.setPassword(password);
		accountObj.setFirstName(firstName);
		accountObj.setLastName(lastName);
		accountObj.setEmail(email);
		accountObj.setAddress(address);

		/* Add the account in the database */
		registrationSuccess = orderProcessorStub.createAccount(accountObj);

		// Send return value accordingly
		if (registrationSuccess)
			System.err.println("Registration Succeeded");
		else
			System.err.println("Registration Not Succeeded");

		return registrationSuccess;
	}

	/**
	 * Method for creating an Address data transfer object
	 * 
	 * @param details
	 * @param province
	 * @param country
	 * @param postalCode
	 * @param phone
	 * @return The address instance
	 */
	static Address createAddressObj(String details, String province,
			String country, String postalCode, String phone) {
		Address addrObj = new Address();

		addrObj.setDetails(details != null ? details.trim() : "");
		addrObj.setProvince(province != null ? province.trim() : "");
		addrObj.setCountry(country != null ? country.trim() : "");
		addrObj.setPostalCode(postalCode != null ? postalCode.trim() : "");
		addrObj.setPhone(phone != null ? phone.trim() : "");
		return addrObj;
	}
}
