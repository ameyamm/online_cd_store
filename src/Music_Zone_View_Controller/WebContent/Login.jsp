<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Music Zone-Login</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<%@ include file="Banner.jsp"%>
	<%@ include file="SideBar.jsp"%>
	
	 <script language="javascript">
	
	 function check()
                {
    			    if(document.form1.username.value=="")
                    {
                        alert("The Username Field is empty.");
                        document.form1.username.focus();
                        return false;
                    }
                    if(document.form1.password.value=="")
                    {
                        alert("The Password Field is empty.");
                        document.form1.password.focus();
                        return false;

                    }
                }
	 </script>
                    

	<div class="shell">
		<div id="content">

			<h4><strong>
				<a href="Reg.jsp"><font color="DodgerBlue"> New User? Register Here! </font></a>
			</strong></h4> <br>

			<c:if test="${servletMessage != ''}">
			<h4><font color="Red">${servletMessage}</font></h4>	
			</c:if><br>
			<table width="90%" border="1">
				<tr>
					<td width="600"><form action="${pageContext.request.contextPath}/login" method="post" name="form1" id="form1" onsubmit="return check()" >
							<table width="100%" height="100" border="0" align="center">
								<tr height="100%">
									<td height="50" colspan="3"><div align="center" class="style4">
											<h3>
												<font color="DodgerBlue">LOGIN</font>
											</h3>
										</div></td>
								</tr>

								<tr>
									<td><span class="style5"></span></td>
									<td><b><font size="2" color="black" face="arial">User Name  :</font></b><br></br></td>
									<td><input type="text" style="height:20px;font-size:10pt;" name="username" size="30"
										maxlength="10" value='' /><br></br></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td width="25%"><span class="style5"></span></td>
									<td><b><font size="2" color="black" face="arial">Password  :</font></b><br></br></td>
									<td><input type="password" style="height:20px;font-size:10pt;" name="password" size="30"
										maxlength="15" value='' /><br></br></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td colspan="4" align="center"><br /> <input type="reset" value="Reset" name="action" style="height:30px; width: 50px;font-size:10pt;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
										<input type="submit" value="Submit" id="login_button" name="action" style="height:30px; width: 60px;font-size:10pt;"> <br /> <br /></td>
								</tr>
							</table>
						</form>
						</td>
				</tr>
			</table>
			<br><br><br><br><br>
		</div>
		<!-- End Content -->
		<div class="cl"></div>
	</div>
	<%@ include file="Footer.jsp"%>

</body>
</html>