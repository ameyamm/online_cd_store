<%@ page import="com.vaham.ws.productcatalog.Cd"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Music Zone-Product Information</title>
</head>

<body>
	<%@ include file="Header.jsp"%>
	<%@ include file="Banner.jsp"%>
	<%@ include file="SideBar.jsp"%>


	<div class="shell">
		<!-- Content -->
		<div id="content">
			<!-- Products -->
			<div class="products">
				<h3>
					<a href="${pageContext.request.contextPath}/categories">Categories</a> --> 
					<a href="${pageContext.request.contextPath}/products?category=${cd.category}">
					${cd.category}
					</a>-->
					${cd.title}
					${servletMessage}
				</h3>
				<center>
				<c:if test="${servletMessage != ''}">
				<font color="Green" size="3"><b>${servletMessage}</b></font><br>	
				</c:if> 
				</center>
				<ul>
					<li>
						<div class="product">
						<form action="${pageContext.request.contextPath}/addToCart" method="POST">
							<span class="holder"> 
							<img src="css/images/itunes9icon.png" alt="Product Image" /> 
									<span><b>Title:</b> ${cd.title} <br></span>
									
									<span><b>Category:</b> ${cd.category}</span>
									<br><br><br>
									<button type="submit" class="buy-btn" style="height:30px; width: 110px;font-size:10pt; border:0 none; cursor:pointer; border-radius: 5px;">Buy @ ${cd.price}</button>
									<br>
									
									<!--  </form>--> 
							</span>
							</form>
						</div>
					</li>
				</ul>
				
				<!-- End Products -->

			</div>
			<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		</div>
		<!-- End Content -->

		<div class="cl">&nbsp;</div>
	</div>
	
	<!-- End Main -->

	<%@ include file="Footer.jsp"%>

</body>
</html>