/**
 * 
 */
package com.vaham.dao.productcatalog;

import com.vaham.dto.CategoryList;
import com.vaham.dto.Cd;
import com.vaham.dto.ProductList;

/**
 * @author mandy
 *
 */
public interface ProductCatalogDAO {
	
	/**
	 * Gets the list of product categories for the store.
	 * @param 
	 * @return CategoryList
	 */
	public CategoryList getCategoryList();
	
	/**
	 * Gets the list of products for a category, 
	 * or all products if no category is specified
	 * @param categoryId
	 * @return ProductList
	 */
	public ProductList getProductList(final String categoryId);
	
	/**
	 * Gets the detailed product information for a product.
	 * @param productId
	 * @return Cd
	 */
	public Cd getProductInfo(final String productId);
}
