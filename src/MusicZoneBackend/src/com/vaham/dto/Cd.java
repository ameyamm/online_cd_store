package com.vaham.dto;

import java.io.Serializable;

/**
 * CD Class
 */
public class Cd implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private String cdId ;
	private String title ; 
	private Long price ; 
	private String category ; 
	
	/**
	 * Constructor for CD Class.
	 * @param cdId
	 * @param title
	 * @param price
	 * @param category
	 */
	public Cd(String cdId, String title, Long price, String category)
	{
		this.cdId = cdId;
		this.title = title;
		this.price = price;
		this.category = category;
	}
	
	public Cd() {
		// TODO Auto-generated constructor stub
	}

	public void setCdId(String cdId) {
		this.cdId = cdId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	/**
	 * @return the cdId
	 */
	public String getCdId() 
	{
		return cdId;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() 
	{
		return title;
	}
	
	/**
	 * @return the price
	 */
	public Long getPrice() 
	{
		return price;
	}
	
	/**
	 * @return the category
	 */
	public String getCategory() 
	{
		return category;
	}
	
}


