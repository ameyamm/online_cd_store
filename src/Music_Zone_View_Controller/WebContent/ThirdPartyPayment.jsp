<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>PayPal-Third Party Payment</title>
</head>
<body bgcolor="DarkGray">
<script language="javascript">
    		 
	 function check()
                {
					 if(document.form1.paymentPassword.value=="")
			         {
			             alert("Enter Password");
			             document.form1.paymentPassword.focus();
			             return false;
			         }
                }
	 </script>
<br><br>
	<center><h1> Third Party Payment </h1></center><br><br>
		<form method="post" action="${pageContext.request.contextPath}/authPayment" name="form1" id="form1" onsubmit="return check()">
			<h3>
			<center><table border="0" >
			<tr height="50" align="left">
				<td width="300">Account/Card Holder Name  :   </td> 
				<td width="200">${nameOnCard}</td>
			</tr>
			<tr height="50" align="left">
				<td>Account/Card ID  :   </td>
				<td>${paymentId}</td>
			</tr>
			
			<tr height="50" align="left">
				<td>Enter your password to validate  :   </td>
				<td><input type="password" name="paymentPassword" value=""></td>
				</tr>
			<tr height="50" >
				<td><input type="reset" value="Reset" name="action" style="height:30px; width: 90px;font-size:10pt;"></td>
				<td><input type="submit" value="Done" name="action" style="height:30px; width: 90px;font-size:10pt;"></td>
			</tr>	
			</table></center>
			</h3>
		</form>
		
	
</body>
</html>