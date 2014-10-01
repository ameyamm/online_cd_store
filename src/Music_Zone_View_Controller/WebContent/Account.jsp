<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Music Zone-Account Information</title>
</head>

<body>
	<%@ include file="Header.jsp"%>
	<%@ include file="Banner.jsp"%>
	<%@ include file="SideBar.jsp"%>


	<div class="shell">
		<div id="content">

			<div align="center">
				<h3>
					<font color="DodgerBlue">Account Information</font>
				</h3>
			</div>

			<table width="100%">
				<tr>
					<td width="700">
						<table width="100%" height="100" border="0" align="center">

							<tr height="100%">
								<td width="6%"><span class="style5"></span></td>
								<td width="25%"><font size="2" color="black" face="arial">First
										Name&nbsp;</font><br></br></td>
								<td width="45%">${firstName}<br></br></td>
								<td width="10%">&nbsp;</td>
							</tr>

							<tr>
								<td><span class="style5"></span></td>
								<td><font size="2" color="black" face="arial">Last
										Name&nbsp;</font><br></br></td>
								<td>${lastName}<br></br></td>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td><span class="style5"></span></td>
								<td><font size="2" color="black" face="arial">Email
										Address</font><br></br></td>
								<td>${email}<br></br></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td><span class="style5"></span></td>
								<td><font size="2" color="black" face="arial">Phone
										Number</font><br></br></td>
								<td>${addrPhone}<br></br></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td><span class="style5"></span></td>
								<td><font face="Arial, helvetica" color="black" size="-1">
										Address</font><br></br></td>
								<td>${addrDetails}&nbsp;${addrProvince}&nbsp;${addrPostalCode}<br></br></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td></td>
								<td><font size="2" color="black" face="arial">Country</font><br></br></td>
								<td>${addrCountry}<br></br></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td><span class="style5"></span></td>
								<td><font size="2" color="black" face="arial">Username</font><br></br></td>
								<td>${username}<br></br></td>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td></td>
								<td><a href="${pageContext.request.contextPath}/categories"><button type="button" style="height:30px; width: 60px;font-size:10pt;">
											Continue</button></a></td>
							</tr>
						</table> <br> <br>
					</td>
				</tr>
			</table>
		</div>
		<!-- End Content -->

		<div class="cl"></div>
	</div>
	<%@ include file="Footer.jsp"%>

</body>
</html>