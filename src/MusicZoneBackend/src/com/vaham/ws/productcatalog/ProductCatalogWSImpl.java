/**
 * 
 */
package com.vaham.ws.productcatalog;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaham.dao.productcatalog.ProductCatalogDAO;
import com.vaham.dao.productcatalog.ProductCatalogDAOImpl;
import com.vaham.dto.CategoryList;
import com.vaham.dto.Cd;
import com.vaham.dto.ProductList;

/**
 * @author mandy
 *
 */
//Service Implementation
@WebService(endpointInterface = "com.vaham.ws.productcatalog.ProductCatalogWS")
public class ProductCatalogWSImpl implements ProductCatalogWS {

	private Logger logger = LogManager.getLogger(ProductCatalogWSImpl.class.getName());

	/**
	 * Gets the list of product categories for the store.
	 * @param 
	 * @return CategoryList
	 */
	@Override
	public CategoryList getCategoryList() {
		/* 
		 * Gets the list of product categories for the store.
		 */
		ProductCatalogDAO pcDAO = new ProductCatalogDAOImpl();
		CategoryList cl = new CategoryList();		
		logger.debug("Start call pcDAO.getCategoryList()...");
		cl = pcDAO.getCategoryList();
		logger.debug("Finish call pcDAO.getCategoryList()...");		
		return cl;
	}

	/**
	 * Gets the list of products for a category, 
	 * or all products if no category is specified
	 * @param categoryId
	 * @return ProductList
	 */
	@Override
	public ProductList getProductList(String categoryId) {
		/* 
		 * Gets the list of products for a category, 
		 * or all products if no category is specified
		 */
		ProductCatalogDAO pcDAO = new ProductCatalogDAOImpl();
		ProductList pl = new ProductList();
		logger.debug("Start call pcDAO.getProductList()...");
		pl = pcDAO.getProductList(categoryId);
		logger.debug("Finish call pcDAO.getProductList()...");
		return pl;
	}

	/**
	 * Gets the detailed product information for a product.
	 * @param productId
	 * @return Cd
	 */
	@Override
	public Cd getProductInfo(String productId) {
		/* 
		 * Gets the detailed product information for a product.
		 */
		ProductCatalogDAO pcDAO = new ProductCatalogDAOImpl();
		logger.debug("Start call pcDAO.getProductInfo()...");
		Cd product = pcDAO.getProductInfo(productId);
		logger.debug("Finish call pcDAO.getProductInfo()...");
		return product;
	}
}
