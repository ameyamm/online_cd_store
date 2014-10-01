package com.vaham.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaham.util.AttributeConstants;
import com.vaham.util.ServletUtils;
import com.vaham.ws.productcatalog.CategoryList;
import com.vaham.ws.productcatalog.Cd;
import com.vaham.ws.productcatalog.ProductCatalogWS;
import com.vaham.ws.productcatalog.ProductCatalogWSImplService;
import com.vaham.ws.productcatalog.ProductInfo;

/**
 * Product manager class for managing the requests to Product related queries.
 * 
 */
public class ProductManager {

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
		if (actionPath.equals("/categories")) {
			processCategoryListing(request, response);
		} else if (actionPath.equals("/products")) {
			processProductListings(request, response);
		} else if (actionPath.equals("/productinfo")) {
			processProductInfoDisplay(request, response);
		}
	}

	/**
	 * Method that processes and manages the response for the request to show
	 * categories.
	 */
	private void processCategoryListing(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletUtils.cleanSessionAttributes(request.getSession());

		String category = (String) request
				.getAttribute(AttributeConstants.CATEGORY_ATTRIB);

		List<String> categories = getCategoryList();
		request.getSession().setAttribute(
				AttributeConstants.CATEGORY_LIST_ATTRIB, categories);

		List<ProductInfo> productsList = getProductsForCategory(category == null ? ""
				: category);

		System.err.println("Product list : "
				+ (productsList.isEmpty() ? " NULL " : productsList.get(0)
						.getTitle()));

		// Managing if productList was empty or not
		if (!productsList.isEmpty()) {
			request.setAttribute(AttributeConstants.PRODUCT_LIST_ATTRIB,
					productsList);
		}

		String lastVisitedPath = request.getRequestURL().toString();

		if (request.getQueryString() != null) {
			lastVisitedPath += ("?" + request.getQueryString());
		}

		request.getSession().setAttribute(
				AttributeConstants.LAST_VISITED_PATH_ATTRIB, lastVisitedPath);

		request.getRequestDispatcher("/Categories.jsp").forward(request,
				response);

	}

	/**
	 * Method that that processes and manages the request for showing product
	 * list of a specific category.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processProductListings(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ServletUtils.cleanSessionAttributes(request.getSession());
		String category = request
				.getParameter(AttributeConstants.CATEGORY_ATTRIB);

		List<ProductInfo> productList = getProductsForCategory(category);

		// Managing if productList was empty or not
		if ((productList != null) && (!productList.isEmpty())) {
			request.setAttribute(AttributeConstants.CATEGORY_ATTRIB, category);

			request.setAttribute(AttributeConstants.PRODUCT_LIST_ATTRIB,
					productList);

			String lastVisitedPath = request.getRequestURL().toString();
			if (request.getQueryString() != null) {
				lastVisitedPath += ("?" + request.getQueryString());
			}

			request.getSession().setAttribute(
					AttributeConstants.LAST_VISITED_PATH_ATTRIB,
					lastVisitedPath);

			request.getRequestDispatcher("/Products.jsp").forward(request,
					response);
		} else {
			/* Handling of incorrect categories. */
		}
	}

	/**
	 * Method that that processes and manages the request for showing detailed
	 * information of a product.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void processProductInfoDisplay(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cdId = request.getParameter(AttributeConstants.CD_ID_ATTRIB);

		Cd cd = getProductInfo(cdId);

		// Managing if returned cd object from web service was empty or not
		if (cd != null) {
			request.getSession().setAttribute(AttributeConstants.CD_ATTRIB, cd);

			String lastVisitedPath = request.getRequestURL().toString();
			if (request.getQueryString() != null) {
				lastVisitedPath += ("?" + request.getQueryString());
			}

			request.getSession().setAttribute(
					AttributeConstants.LAST_VISITED_PATH_ATTRIB,
					lastVisitedPath);

			request.getRequestDispatcher("/ProductInfo.jsp").forward(request,
					response);
		} else {
			/* Handling of incorrect categories. */
		}
	}

	/**
	 * Will call the getProductList(categoryid) from Product Catalog Service
	 * which will get the list of product categories for the store.
	 * 
	 * @return The list of categories from database.
	 */
	private List<String> getCategoryList() {
		// Call the client stub of webservice.
		ProductCatalogWSImplService productCatalogWSImplService = new ProductCatalogWSImplService();
		ProductCatalogWS productCatalogService = productCatalogWSImplService
				.getProductCatalogWSImplPort();

		CategoryList categoryList = productCatalogService.getCategoryList();
		List<String> categories = categoryList.getCategoryList();
		
		// debugging
		System.out.println("CATEGORY LISTS: "
				+ (categories == null ? null : categories));
		return categories;
	}

	/**
	 * Will call the getProductList(categoryid) from Product Catalog Service
	 * which will get the list of products for a category, or all products if no
	 * category is specified.
	 * 
	 * @param category
	 * @return a list ProductInfos of a category.
	 */
	private List<ProductInfo> getProductsForCategory(String categoryId) {
		// Call the client stub of webservice.
		ProductCatalogWSImplService productCatalogWSImplService = new ProductCatalogWSImplService();
		ProductCatalogWS productCatalogService = productCatalogWSImplService
				.getProductCatalogWSImplPort();

		List<ProductInfo> categoryProducts = productCatalogService
				.getProductList(categoryId).getProductList();

		return categoryProducts;
	}

	/**
	 * Will call the getProductInfo(productid) from Product Catalog Service
	 * which will get the detailed product information for a product.
	 * 
	 * @param CdId
	 * @return CD object from web service
	 */
	private Cd getProductInfo(String CdId) {
		// Call the client stub of webservice
		ProductCatalogWSImplService productCatalogWSImplService = new ProductCatalogWSImplService();
		ProductCatalogWS productCatalogService = productCatalogWSImplService
				.getProductCatalogWSImplPort();

		Cd cd = productCatalogService.getProductInfo(CdId);
		return cd;
	}

}
