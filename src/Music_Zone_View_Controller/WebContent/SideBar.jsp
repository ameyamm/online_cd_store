<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="css/images/favicon.ico" />
	<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
	<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
	<script type="text/javascript" src="js/jquery.jcarousel.min.js"></script>
	<script type="text/javascript" src="js/functions.js"></script>
<title>Insert title here</title>
</head>
<body>
<!-- Main -->
	<div id="main" class="shell">
	
		<!-- Sidebar -->
		<div id="sidebar">
			<ul class="categories">
				<li>
				<c:if test="${not empty categoryList}">
					<h4>Categories</h4>
					<ul>
					<c:forEach items="${categoryList}" var="categoryName">
						<li><a href="${pageContext.request.contextPath}/products?category=${categoryName}"><span>${categoryName}</span></a></li>
					</c:forEach>
					</ul>
					</c:if>
				</li>
			</ul>
		</div>
		<!-- End Sidebar -->
	</div>
</body>
</html>