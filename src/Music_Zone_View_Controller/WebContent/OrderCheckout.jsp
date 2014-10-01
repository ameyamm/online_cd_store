<%@ page
	import="com.vaham.shoppingcart.ShoppingCart,com.vaham.ws.orderprocessor.Address,com.vaham.ws.orderprocessor.PurchaseOrder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Music Zone-Checkout</title>
</head>

<body>
	<%@ include file="Header.jsp"%>
	<%@ include file="Banner.jsp"%>
	<%@ include file="SideBar.jsp"%>
	<script language="javascript">
    		 
	function checkPhone(addrPhone)
	 {
		 var pattern = /[0-9.-]+/;
		 return (pattern).test(addrPhone);
	 } 
	function check1()
                {
    			
    			 var c=document.form1.addrPhone.value;
    			   
                    if(document.form1.addrPhone.value=="")
                    {
                        alert("Enter phone number ");
                        document.form1.addrPhone.focus();
                        return false;
                    }
                    else if(!checkPhone(document.form1.addrPhone.value))
                    {
                        alert("Please enter correct number");
                        document.form1.addrPhone.focus();
                        return false;
                    }
                    else if(((document.form1.addrPhone.value.length)<10) || ((document.form1.addrPhone.value.length)>15))
                    {
                        alert("Enter correct Number");
                        document.form1.addrPhone.focus();
                        return false;
                    }

                    if(document.form1.addrDetails.value=="")
                    {
                        alert("Enter Street Details ");
                        document.form1.addrDetails.focus();
                        return false;
                    }
                    
                    if(document.form1.addrProvince.value=="")
                    {
                        alert("Enter Province ");
                        document.form1.addrProvince.focus();
                        return false;
                    }
                    
                    if(document.form1.addrCountry.value=="")
                    {
                        alert("Enter Country ");
                        document.form1.addrCountry.focus();
                        return false;
                    }
                    
                    if(document.form1.addrPostalCode.value=="")
                    {
                        alert("Enter Postal Code ");
                        document.form1.addrPostalCode.focus();
                        return false;
                    }

                    return true;
                }
			</script>
	<div class="shell">
		<!--content-->
		<div id="content">

			<div class="products">
				<center>
					<h3>Order Checkout</h3>
				</center>
			</div>

			<table width="100%" height="50" border="0" align="center">
				
				<tr height="100%"> 
					<td width="25%"><font size="2" color="black" face="arial"><b>TITLE</b></font></td>
					<td width="25%"><font size="2" color="black" face="arial"><b>CATEGORY</b></font></td>
					<td width="15%"><font size="2" color="black" face="arial"><b>PRICE($)</b></font></td>
				</tr>
				<c:if test="${servletMessage !=  ''}">
					<h4><font color="Red">${servletMessage}</font></h4>
				</c:if>
				<c:forEach items="${shoppingCartList}" var="cart">
					<tr>
						<td><c:out value="${cart.title}" /></td>
						<td><c:out value="${cart.category}" /></td>
						<td><c:out value="${cart.price}" /></td>
					</tr>
				</c:forEach>
				<tr height="100%">
					<td></td>
					<td align="center"><b><font color="navy" size="2">TOTAL (inclusive of 10% Tax):</font></b></td>
					<td align="left"><b><font color="navy" size="2">${wsPurchaseOrder.totalPrice}</font></b></td>
				</tr>
			</table>
			<br>
			<form method="post"
				action="${pageContext.request.contextPath}/paymentManager" onsubmit="return check1()" name="form1" id="form1" >
				<table width="100%" height="50" border="0" align="center" >
					<tr height="100%" align="center">

						<td><font size="3" color="black" face="arial"><b>Shipping Address: </b></font></td>
					<tr height="100%">
						<td><span class="style5"></span></td>
						<td><font face="Arial, helvetica" color="black" size="-1">
								Street Address :</font> <br></br></td>
						<td><input class="USS_TEXT" maxlength="30" name="addrDetails"
							size="30" value="${address.details}" style="height:20px;font-size:10pt;" /><br></br></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="100%">
						<td><span class="style5"></span></td>
						<td><font face="Arial, helvetica" color="black" size="-1">Province :</font><br></br></td>
						<td><input type="text" name="addrProvince" size="15"
							maxlength="15" value="${address.province}" style="height:20px;font-size:10pt;" /><br></br></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="100%">
						<td><span class="style5"></span></td>
						<td><font face="Arial, helvetica" color="black" size="-1">Postal
								Code :</font><br></br></td>
						<td><input class="USS_TEXT" maxlength="6"
							name="addrPostalCode" size="6" value="${address.postalCode}" style="height:20px;font-size:10pt;" /><br></br></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="100%">
						<td></td>
						<td><font size="2" color="black" face="arial">Country :</font><br></br></td>
						<td><input type="text" name="addrCountry" size="30"
							maxlength="30" value="${address.country}" style="height:20px;font-size:10pt;" /><br></br></td>
						<td>&nbsp;</td>
					</tr>
					<tr height="100%">
						<td><span class="style5"></span></td>
						<td><font size="2" color="black" face="arial">Phone
								Number :</font><br></br></td>
						<td><input type="text" name="addrPhone" maxlength="10"
							size="10" value="${address.phone}" style="height:20px;font-size:10pt;" /><br></br></td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						
						<td width="25%"><input type="hidden" name="mode"
							value="proceedPayment"> <input type="submit"
							value="Proceed to Payment" name="action"
							style="height: 30px; width: 150px; font-size: 10pt;"></td>
					</tr>
				</table>
			</form>
			<br>
			<form method="post" action="${pageContext.request.contextPath}//viewCart">
				<table>
					<tr>
						<td></td>
						<td><input type="submit" value="Back" name="action"
							style="height: 30px; width: 90px; font-size: 10pt;"></td>
					</tr>
				</table>
			</form>
			<br><br>
		</div>
		<!-- End Content -->
		<div class="cl"></div>
	</div>
	<%@ include file="Footer.jsp"%>

</body>
</html>