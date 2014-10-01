<%@ page import="com.vaham.shoppingcart.ShoppingCart"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Music Zone-Cart</title>
</head>

<body>
	<%@ include file="Header.jsp"%>
	<%@ include file="Banner.jsp"%>
	<%@ include file="SideBar.jsp"%>

	<div class="shell">
		<!--content-->
		<div id="content">

			<div class="products">
				<center>
					<h3><font color="DodgerBlue">My Cart</font></h3>
				</center>
			</div>
			<br>
			<c:if test="${empty loggedUsername}">
			<h4> Please Login to View Cart.</h4>
			</c:if>
			<c:if test="${not empty loggedUsername}">
			<table width="100%" height="80" border="0" align="center">
				<tr height="110%" align="center">
					<b>
						<td width="25%"><font size="3">TITLE</td>
						<td width="25%"><font size="3">CATEGORY</td>
						<td width="15%"><font size="3">PRICE($)</td>
					</b>
				</tr>
				<center>
				<c:if test="${servletMessage !=  ''}">
				<font color="red" size="3">${servletMessage}</font>
				</c:if><br>
				</center>
				<c:forEach items="${shoppingCartList}" var="cart">
					<form method="POST"
						action="${pageContext.request.contextPath}/removeFromCart">
						<input type="hidden" name="cdId" value="${cart.cdId}" />
						<tr>
							<td><c:out value="${cart.title}" /></td>
							<td align="center"><c:out value="${cart.category}" /></td>
							<td align="center"><c:out value="${cart.price}" /></td>
							<td align="center" width="25%"><input type="submit" value="Remove"	name="action" style="height:30px; width: 70px;font-size:10pt;"></td>
						</tr>
					</form>
				</c:forEach>

				<tr height="100%" align="center">
					<td></td>
					<td><font size="3"><b>TOTAL :</b></font></td>
					<td><font size="3"><b>${shoppingCart.totalPrice}</b></font></td>
				</tr>
	

			</table>
			<br>
			<a href="${pageContext.request.contextPath}/categories"><input type="submit" value="Buy Items" name="action" style="height:30px; width: 120px;font-size:10pt;"></a>
			<c:if test="${not empty shoppingCartList}">
			<form method="post" action="${pageContext.request.contextPath}/orderCheckout">
			<br>
			<input type="submit" value="Checkout" name="action" style="height:30px; width: 90px;font-size:10pt;">
			</form>
			</c:if>
			<br><br><br><br><br>
			</c:if>
		</div>

		<!-- End Content -->
		<div class="cl"></div>
	</div>
	<%@ include file="Footer.jsp"%>

</body>
</html>