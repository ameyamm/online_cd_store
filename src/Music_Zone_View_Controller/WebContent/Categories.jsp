<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Music Zone-Categories</title>
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
				<center>
					<h3>Items</h3>
				</center>
				<ul>
					<c:forEach items="${productList}" var="product">
						<li>
							<div class="product">
								<span class="holder"> 
								<img src="css/images/itunes9icon.png" alt="Product Image" /> <!-- <a href="c_country.jsp"> -->
									<a href="${pageContext.request.contextPath}/productinfo?cdId=${product.cdId}">
									${product.title}</a>
									</span>
							</div>
						</li>
					</c:forEach>
				</ul>
				<!-- End Products -->
			</div>
		</div>
		<!-- End Content -->
		<div class="cl">&nbsp;</div>
	</div>
	<!-- End Main -->

	<%@ include file="Footer.jsp"%>

</body>
</html>