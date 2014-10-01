/**
 * 
 */
package com.vaham.dao.productcatalog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaham.dao.QueryIDManager;
import com.vaham.dbagent.DBManager;
import com.vaham.dto.CategoryList;
import com.vaham.dto.Cd;
import com.vaham.dto.ProductInfo;
import com.vaham.dto.ProductList;

/**
 * @author mandy
 * 
 */
public class ProductCatalogDAOImpl implements ProductCatalogDAO {

	DBManager connectionManager = new DBManager();

	private Logger logger = LogManager.getLogger(ProductCatalogDAOImpl.class
			.getName());

	/**
	 * Gets the list of product categories for the store.
	 * 
	 * @param
	 * @return CategoryList
	 */
	public CategoryList getCategoryList() {
		ArrayList<String> parameterList = new ArrayList<String>();
		CategoryList cl = new CategoryList();
		ArrayList<String> categoryList = new ArrayList<String>();
		String type = "Type";

		connectionManager.init();
		List<Map<String, Object>> clList = connectionManager.getQueryResult(
				QueryIDManager.SELECT_CATEGORY_LIST, parameterList);
		logger.debug("Returned category list size is: " + clList.size());

		/**
		 * Retrieve the category from database returned result: The map.getValue
		 * string is like: enum('COUNTRY','POP','ROCK') So first substring this
		 * value and then split to get those 3 categories: COUNTRY POP ROCK Then
		 * put the above in a list.
		 */
		for (Entry<String, Object> map : clList.get(0).entrySet()) {
			// logger.debug("map.getKey():" + map.getKey());
			if (type.equalsIgnoreCase(map.getKey())) {
				String cltmp = (String) map.getValue();
				int length = cltmp.length();
				cltmp = cltmp.substring(5, length - 1);
				logger.debug("cltmp.substring(5, length-1): " + cltmp);
				for (String str : cltmp.split(",")) {
					categoryList.add(str.substring(1, str.length() - 1));
				}
			}
		}

		connectionManager.finalize();
		logger.debug("Retrieved Category List is: " + categoryList.toString());
		cl.setCategoryList(categoryList);
		return cl;
	}

	/**
	 * Gets the list of products for a category, or all products if no category
	 * is specified
	 * 
	 * @param categoryId
	 * @return ProductList
	 */
	public ProductList getProductList(final String categoryId) {
		ArrayList<String> parameterList = new ArrayList<String>();
		ProductList pl = new ProductList();
		ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
		List<Map<String, Object>> plList = new ArrayList<Map<String, Object>>();
		String cdid = "cdid";
		String title = "title";
		
		connectionManager.init();
		if (null == categoryId || categoryId.length() <=0 ) {
			logger.debug("Category is null, will get all product's list. ");
			plList = connectionManager.getQueryResult(QueryIDManager.SELECT_PRODUCT_LIST,
					parameterList);
			logger.debug("Returned Product List size is: " + plList.size());
		} else {
			logger.debug("Category is not null, will get product's list of category: "
					+ categoryId);
			parameterList.add(0, categoryId);
			plList = connectionManager.getQueryResult(
					QueryIDManager.SELECT_PRODUCT_LIST_BY_CATEGORY, parameterList);
			logger.debug("Returned Product List size is: " + plList.size());
		}

		/**
		 * Retrieve Returned productinfo from database
		 */
		for (int i = 0; i < plList.size(); i++) {
			logger.debug("plList.get(i)-" + i + "-" + plList.get(i));
			ProductInfo cdtmp = new ProductInfo();
			for (Map.Entry<String, Object> entry : plList.get(i).entrySet()) {

				if (cdid.equalsIgnoreCase(entry.getKey())) {
					cdtmp.setCdId((String) entry.getValue());
				} else if (title.equalsIgnoreCase(entry.getKey())) {
					cdtmp.setTitle((String) entry.getValue());
				}
				// logger.debug(cdtmp.getCdId()+"|"+cdtmp.getTitle()+"|");
				// else if("price".equalsIgnoreCase(entry.getKey())){
				// cdtmp.setPrice((Long) entry.getValue());
				// }else if ("category".equalsIgnoreCase(entry.getKey())){
				// cdtmp.setCategory((String) entry.getValue());
				// }
				// System.out.println(ccc.getCdId()+"|"+ccc.getTitle()+"|"+ccc.getCategory()+"|"+ccc.getPrice());
			}
			productList.add(cdtmp);
		}

		connectionManager.finalize();
		pl.setProductList(productList);

		return pl;
	}

	/**
	 * Gets the detailed product information for a product.
	 * 
	 * @param productId
	 * @return Cd
	 */
	public Cd getProductInfo(final String productId) {

		/**
		 * If input productId is null, return a null Cd
		 */
		if (null == productId){
			logger.error("Error occured, input productId is null.");
			return new Cd();
		} 
		
		ArrayList<String> parameterList = new ArrayList<String>();
		parameterList.add(0, productId);
		List<Map<String, Object>> pl = new ArrayList<Map<String, Object>>();
		String cdid = "cdid";
		String title = "title";
		String price = "price";
		String category = "category";
		
		connectionManager.init();
		pl = connectionManager
				.getQueryResult(QueryIDManager.SELECT_PRODUCT_BY_ID, parameterList);
		// logger.debug("Returned Product size: " + pl.size());
		Cd cdtmp = new Cd();
		if (pl.size() == 1) {
			logger.debug("Returned Product: " + pl.get(0));

			for (Map.Entry<String, Object> entry : pl.get(0).entrySet()) {

				if (cdid.equalsIgnoreCase(entry.getKey())) {
					cdtmp.setCdId((String) entry.getValue());
				} else if (title.equalsIgnoreCase(entry.getKey())) {
					cdtmp.setTitle((String) entry.getValue());
				} else if (price.equalsIgnoreCase(entry.getKey())) {
					cdtmp.setPrice((Long) entry.getValue());
				} else if (category.equalsIgnoreCase(entry.getKey())) {
					cdtmp.setCategory((String) entry.getValue());
				}
			}
			logger.debug(cdtmp.getCdId() + "|" + cdtmp.getTitle() + "|"
					+ cdtmp.getCategory() + "|" + cdtmp.getPrice());
		} else {
			logger.error("Errors occured, there is no such Cd or multi Cd. Returned Product size is: "
					+ pl.size());
		}

		connectionManager.finalize();

		return cdtmp;
	}

	public static void main(String[] args) {

//		ProductCatalogDAOImpl pcDAO = new ProductCatalogDAOImpl();
		// CategoryList cl = pcDAO.getCategoryList();

		// ProductList pl = pcDAO.getProductList(null);
		// ProductList pl1 = pcDAO.getProductList("POP");

//		Cd cd = pcDAO.getProductInfo("cd0001");
		// for (String str : "enum('COUNTRY','POP','ROCK')".substring(5, 27)
		// .split(","))
		// System.out.println(str.substring(1, str.length()-1));

	}

}
