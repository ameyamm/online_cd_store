<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!String loginid = "";%>
<html lang="en-US" dir="ltr">
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="css/images/favicon.ico" />
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery.jcarousel.min.js"></script>
<script type="text/javascript" src="js/JSfuntions.js"></script>
</head>
<body>

	<%
		String username = (String) request.getSession().getAttribute("username");
	%>

	<!-- Header -->
	<div id="header" class="shell">
		<div id="logo">
			<a href="Home.jsp"><img src="css/images/abc.png" align="right" alt="" /></a>
			<h1>
				<a href="Home.jsp">MUSIC ZONE </a>
			</h1>
			<span><a href="Home.jsp"><font size="2">Online CD Store</font></a></span>
		</div>

		<!-- Navigation -->
		<div id="navigation">
			<ul>
				<li><a href="Home.jsp">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/categories">Categories</a></li>
				<li><a href="FM.jsp">FM Streaming</a></li>
				<li><a href="AboutUs.jsp">About Us</a></li>
			</ul>
		</div>
		<!-- End Navigation -->

		<div class="cl"></div>

		<!-- Login-details -->

		<c:if test="${empty loggedUsername}" >
		<div id="login-details">
			<p>
				Welcome &nbsp; Guest &nbsp;&nbsp;&nbsp;
			</p>
			<a href="Login.jsp" id="user"> Login</a>
		</div>
		</c:if>
		<c:if test="${not empty loggedUsername}" >
		<div id="login-details">
			<p>
				Welcome &nbsp;
				<a href="${pageContext.request.contextPath}/account" id="user">${loggedUsername}</a>
			</p>
			
			<p>&nbsp;&nbsp;&nbsp;
			<a href="${pageContext.request.contextPath}/logout">	Logout</a>
			</p>
			<form method="post" action="${pageContext.request.contextPath}/viewCart">
					<input type="submit" class="cart"  value= "My Cart" id="abc"/>
			</form>
				
		</div>
		</c:if>
		<!-- End Login-details -->

	</div>
	<!-- End Header -->
</body>
</html>