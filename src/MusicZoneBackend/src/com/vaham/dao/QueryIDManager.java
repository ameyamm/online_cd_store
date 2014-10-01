package com.vaham.dao;

/**
 * A class for storing all the query ids in the config file used
 * by the dbagent.
 */
public class QueryIDManager 
{
	/* PurchaseOrder table queries */
	
	// Insert queries
	public static final String INSERT_PURCHASE_ORDER_QRY = "INPO001" ;
	
	// Select queries
	public static final String SELECT_MAX_PURCHASE_ORDER_ID_QRY_USING_USERNAME = "SEPO001" ;
	
	/* PurchaseOrderItem Table queries */
	public static final String INSERT_PURCHASE_ORDER_ITEM_QRY = "INPI001" ;
	
	/* Account table queries */
	
	// Insert queries
	public static final String INSERT_ACCOUNT_QRY = "INAC001" ;
	
	// Select Queries
	public static final String SELECT_ACCOUNT_QRY_USING_USERNAME = "SEAC001" ;

	/* Address table queries */
	
	// Select queries
	public static final String SELECT_ADDRESS_QRY_USING_ADDR_ID = "SEAD001" ;
	public static final String SELECT_MAX_ADDR_ID_QRY_USING_POSTAL_CODE = "SEAD002" ;
	
	// Insert queries
	public static final String INSERT_ADDRESS_QRY = "INAD001" ;
	

	/**
	 * ProductCatalog 
	 */
	public static final String SELECT_CATEGORY_LIST = "queryCategoryList";
	public static final String SELECT_PRODUCT_LIST = "queryProductListAll";
	public static final String SELECT_PRODUCT_LIST_BY_CATEGORY = "queryProductListByCategory";
	public static final String SELECT_PRODUCT_BY_ID = "queryProductById";
	
}
