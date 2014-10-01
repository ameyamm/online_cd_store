<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Music Zone-Payment</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<%@ include file="Banner.jsp"%>
	<%@ include file="SideBar.jsp"%>
	 <script language="javascript">
    		 
	 function check()
                {
					 if(document.form1.paymentType.value=="")
			         {
			             alert("Specify Payment Type");
			             document.form1.paymentType.focus();
			             return false;
			         }
		 			if(document.form1.paymentId.value=="")
                    {
                        alert("Enter Card Number");
                        document.form1.paymentId.focus();
                        return false;
                    }
    			    else if(((document.form1.paymentId.value.length)<16) || ((document.form1.paymentId.value.length)>16))
                    {
                        alert("Card no should contain 16 integers ");
                        document.form1.paymentId.focus();
                        return false;
                    }
                    
                    if(document.form1.nameOnCard.value=="")
                    {
                        alert("Enter Name on Card");
                        document.form1.nameOnCard.focus();
                        return false;
                    }
                   
                    if(document.form1.cardtype.value=="")
                    {
                        alert("Specify Card Type");
                        document.form1.cardtype.focus();
                        return false;
                    }

                }
	 </script>
                    

	<div class="shell">
		<div id="content">

					<form action="${pageContext.request.contextPath}/paymentManager" method="post" name="form1" id="form1" onsubmit="return check()">
							<table width="100%" height="100" border="0" align="center">
								<tr height="50%">
									<td height="20" colspan="3"><div align="center" class="style4">
											<h3>
												<font color="DodgerBlue">Payment</font>
											</h3>
											<h4>
												<b><font color="DodgerBlue">Card Details</font></b>
											</h4>
										</div></td>
								</tr>
								<tr>
									<td width="10%"><span class="style5"></span></td>
									<td width="25%"><br><b><font size="2" color="black" face="arial">Payment Type :</font></b><br></td>
									<td width="35%"><br><select name="paymentType" style="height: 20px; width: 200px; font-size: 14px;" >
                                        <option  value="">--SELECT--</option>
                                        <option  value="CREDIT_CARD">Credit Card</option>
                                        <option  value="DEBIT_CARD">Debit Card </option>
                                        <option  value="INTERNET_BANKING">Internet Banking </option>
                                </select>              </td>
                                </tr>
								
								<tr>
									<td ><span class="style5"></span></td>
									<td><br><b><font size="2" color="black" face="arial">Card Number :</font></b><br></br></td>
									<td width="15%"><br><input type="text" autocomplete="off" style="height: 20px; width: 200px; font-size: 14px;" name="paymentId" size="30"
										maxlength="16" value='' /><br></br></td>
									
								</tr>
								<tr>
									<td width="5%"><span class="style5"></span></td>
									<td><b><font size="2" color="black" face="arial">Name on Card / Account :</font></b><br></br></td>
									<td><input type="text" autocomplete="off" style="height: 20px; width: 200px; font-size: 14px;" name="nameOnCard" size="30"
										maxlength="30" value='' /><br></br></td>
								</tr>
								<tr>
									<td colspan="4" align="center"><br /> <input type="reset" style="height: 30px; width: 90px; font-size: 10pt;" />
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="hidden" name="mode" value="validatePayment"> 
										<input type="submit" value="Submit"  style="height: 30px; width: 90px; font-size: 10pt;" /> <br /> <br /></td>
								</tr>
							</table>
						</form>
		</div>
		<!-- End Content -->
		<div class="cl"></div>
	</div>
	<%@ include file="Footer.jsp"%>

</body>
</html>