/**
 * 
 */
package com.vaham.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author mandy
 *
 */
public class ProductList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	private ArrayList<ProductInfo> productList;

	public ArrayList<ProductInfo> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<ProductInfo> productList) {
		this.productList = productList;
	}

}
