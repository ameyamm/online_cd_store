/**
 * 
 */
package com.vaham.util;

import javax.servlet.http.HttpSession;

/**
 * @author Mihir
 *
 */
public class ServletUtils 
{

	public static void cleanSessionAttributes(HttpSession session)
	{
		session.removeAttribute(AttributeConstants.CD_ATTRIB);
	}
}
