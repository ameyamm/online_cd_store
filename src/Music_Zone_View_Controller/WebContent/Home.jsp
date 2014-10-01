<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Music Zone-Home Page</title>
</head>

<body>
<%@ include file="Header.jsp" %>
<%@ include file="Banner.jsp" %>	
<%@ include file="SideBar.jsp" %>
	
	<div class="shell">
		<div id="content">
						
			<p><font color="MidnightBlue " size="3"> Buy Original music CD's here. Wide range of Music Genres and Artists at lowest prices in Canada..!!
			<br><br>Latest mp3 songs audio CD's and movie album songs online in Canada. <br>Order upcoming movie songs, latest movie and album songs CDs here.</font></p>
			<br><br><br><br><br><br><br><br><br>
						
		</div>
		<!-- End Content -->
	<div class="cl">
	</div><center><font size="4" color="DodgerBlue" face ="times new roman">	
	
			<%
			    Integer hitsCount =(Integer)application.getAttribute("hitCounter");
			    if( hitsCount ==null || hitsCount == 0 ){
			       /* First visit */
			       hitsCount = 1;
			       out.println("You are visitor number : " +hitsCount);
			       
			    }else{
			       /* return visit */
			       hitsCount += 1;
			       out.println("You are visitor number : " +hitsCount);
			       
			    }
			    application.setAttribute("hitCounter", hitsCount);
			%>
			</font></center></div>
			
			
<%@ include file="Footer.jsp" %>

</body>
</html>