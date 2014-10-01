/**
 * 
 */
package com.vaham.dto;

import java.io.Serializable;

/**
 * @author mandy
 *
 */
public class ProductInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdId ;
	private String title ;
	
	public String getCdId() {
		return cdId;
	}
	public void setCdId(String cdId) {
		this.cdId = cdId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	} 
}
