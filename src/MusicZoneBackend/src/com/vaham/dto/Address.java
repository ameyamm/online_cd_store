/**
 * 
 */
package com.vaham.dto;

import java.io.Serializable;

/**
 * Address class for storing address info.
 */
public class Address implements Serializable 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long addrID ; 
	private String details;
	private String province;
	private String country;
	private String postalCode; 
	private String phone;

	/**
	 * Default Constructor
	 */
	
	public Address()
	{
		// Nothing to do.
	}
	
	/**
	 * @param street
	 * @param province
	 * @param country
	 * @param zip
	 * @param phone
	 */
	public Address(String details, String province,
			String country, String postalCode, String phone) {
		this.details = details;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.phone = phone;
	}

	
	/**
	 * Copy constructor
	 * @param address
	 */
	public Address(Address address)
	{
		this.details = address.details;
		this.province = address.province;
		this.country = address.country;
		this.postalCode = address.postalCode;
		this.phone = address.phone;
	}
	
	/**
	 * @return the addrID
	 */
	public long getAddrID() {
		return addrID;
	}


	/**
	 * @param addrID the addrID to set
	 */
	public void setAddrID(long addrID) {
		this.addrID = addrID;
	}


	/**
	 * @return the street
	 */
	public String getDetails() 
	{
		return details;
	}

	/**
	 * @param street the street to set
	 */
	public void setDetails(String details) 
	{
		this.details = details;
	}
	
	/**
	 * @return the province
	 */
	public String getProvince() 
	{
		return province;
	}
	
	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) 
	{
		this.province = province;
	}
	
	/**
	 * @return the country
	 */
	public String getCountry() 
	{
		return country;
	}
	
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * @return the zip
	 */
	public String getPostalCode() {
		return postalCode;
	}
	
	/**
	 * @param zip the zip to set
	 */
	public void setPostalCode(String postalCode) 
	{
		this.postalCode = postalCode;
	}
	
	/**
	 * @return the phone
	 */
	public String getPhone() 
	{
		return phone;
	}
	
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean equals(Address addrObj) 
	{
		if( this.country.equals(addrObj.country) &&
				this.postalCode.equals(this.postalCode) &&
				this.province.equals(addrObj.province) &&
				this.phone.equals(addrObj.phone) &&
				this.details.equals(addrObj.details) )
		{
			return true ; 
		}
		return false ; 
	}
}
