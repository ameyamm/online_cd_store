/**
 * 
 */
package com.vaham.dto;

import java.io.Serializable;


public class PaymentInfo implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String paymentType ;
	private String paymentIDNumber ;  // This would store credit/debit/account number
	private String paymentStatus ; 
 	/**
	 * @return the paymentType
	 */
	public String getPaymentType() 
	{
		return paymentType;
	}
	
	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
	/**
	 * @return the paymentIDNumber
	 */
	public String getPaymentIDNumber() 
	{
		return paymentIDNumber;
	}
	
	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * @param paymentIDNumber the paymentIDNumber to set
	 */
	public void setPaymentIDNumber(String paymentIDNumber) {
		this.paymentIDNumber = paymentIDNumber;
	} 
}
