<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
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
				<h3>
					<!-- Checking if such category is in database -->
					<a href="${pageContext.request.contextPath}/categories">CATEGORY</a> : ${category}

					<c:if test="${servletMessage != ''}">
						<h4>${servletMessage}</h4>
					</c:if>

				</h3>
				<ul>
					<c:forEach items="${productList}" var="product">
						<li>
							<div class="product">
								<span class="holder"> <img src="css/images/itunes9icon.png" alt="Product Image" />
									<a href="${pageContext.request.contextPath}/productinfo?cdId=${product.cdId}"> ${product.title}</a>
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