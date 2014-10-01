/**
 * 
 */
package com.vaham.ws.productcatalog;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.vaham.dto.CategoryList;
import com.vaham.dto.Cd;
import com.vaham.dto.ProductList;



/**
 * @author mandy
 *
 */
@WebService
@SOAPBinding(style = Style.RPC)

public interface ProductCatalogWS {

	@WebMethod public CategoryList getCategoryList();
	@WebMethod public ProductList getProductList(@WebParam(name = "categoryId")String categoryId);
	@WebMethod public Cd getProductInfo(@WebParam(name = "productId")String productId);
}
