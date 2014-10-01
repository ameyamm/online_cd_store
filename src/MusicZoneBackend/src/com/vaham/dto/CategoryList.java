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
public class CategoryList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	private ArrayList<String> categoryList;

	public ArrayList<String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(ArrayList<String> categoryList) {
		this.categoryList = categoryList;
	}
	

}
