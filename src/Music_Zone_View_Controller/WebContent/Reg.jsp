<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Music Zone-Registration</title>
</head>
<body>
<%@ include file="Header.jsp" %>
<%@ include file="Banner.jsp" %>	
<%@ include file="SideBar.jsp" %>
	 <script language="javascript">
    		 
	 function checkEmail(email)
	 {
		 var pattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
		 return (pattern).test(email);
	 }
	 
	 function checkPostalCode(code)
	 {
		 var pattern = /^[a-zA-Z0-9]{6,7}$/;
		 return (pattern).test(code);
	 }
	 
	 function check()
                {
    			 var a=document.form1.firstName.value;
    			 var b=document.form1.lastName.value;
    			 var c=document.form1.addrPhone.value;
    			 
                    if(document.form1.firstName.value=="")
                    {
                        alert("Enter Your First Name");
                        document.form1.firstName.focus();
                        return false;
                    }
                    else if(parseInt(a))
                    {
                        alert("Enter name Correctly");
                        document.form1.firstName.focus();
                        return false;

                    }
                    
                    if(document.form1.lastName.value=="")
                    {
                        alert("Enter Your Last Name");
                        document.form1.lastName.focus();
                        return false;
                    }
                    else if(parseInt(b))
                    {
                        alert("Enter name Correctly");
                        document.form1.lastName.focus();
                        return false;

                    }
                   	
                    if(document.form1.email.value=="")
                    {
                        alert("Email field should not be empty ");
                        document.form1.email.focus();
                        return false;
                    }
                    else if(!checkEmail(document.form1.email.value))
                    {
                        alert("Please enter correct email");
                        document.form1.email.focus();
                        return false;
                    }
                    
                    
                    if(document.form1.addrPhone.value=="")
                    {
                        alert("Enter phone number ");
                        document.form1.addrPhone.focus();
                        return false;
                    }
                    else if(!parseInt(c))
                    {
                        alert("Phone number should only contain digits");
                        document.form1.addrPhone.focus();
                        return false;
                    }
                    else if(((document.form1.addrPhone.value.length)<10) || ((document.form1.addrPhone.value.length)>15))
                    {
                        alert("Phone no: should contain atleast 10 digits ");
                        document.form1.addrPhone.focus();
                        return false;
                    }

                    if(document.form1.addrDetails.value=="")
                    {
                        alert("Enter Street Details ");
                        document.form1.addrDetails.focus();
                        return false;
                    }
                    
                    if(document.form1.addrProvince.value=="")
                    {
                        alert("Enter Province ");
                        document.form1.addrProvince.focus();
                        return false;
                    }                 
                    
                    if(document.form1.addrPostalCode.value=="")
                    {
                        alert("Enter Postal Code ");
                        document.form1.addrPostalCode.focus();
                        return false;
                    } else if (!checkPostalCode(document.form1.addrPostalCode.value))
                    	{
                    	alert("Enter Postal Code in correct format");
                        document.form1.addrPostalCode.focus();
                        return false;
                    	}
                    
                    if(document.form1.addrCountry.value=="")
                    {
                        alert("Enter Country ");
                        document.form1.addrCountry.focus();
                        return false;
                    }

                    if(document.form1.username.value=="")
                    {
                        alert("Enter Username");
                        document.form1.username.focus();
                        return false;
                    }
                    
                    if(document.form1.password.value=="")
                    {
                        alert("Enter Password");
                        document.form1.password.focus();
                        return false;

                    }
                    else if((document.form1.password.value.length)<6)
                    {
                        alert("Password should contain atleast 6 characters ");
                        document.form1.password.focus();
                        return false;
                    }
                    
                    
                    if(document.form1.cnfrmpassword.value=="")
                    {
                        alert("Enter Password again");
                        document.form1.cnfrmpassword.focus();
                        return false;

                    }
                    else if((document.form1.cnfrmpassword.value) != (document.form1.password.value))
                    {
                        alert("Passwords do not match ");
                        document.form1.cnfrmpassword.focus();
                        return false;
                    }
                    

                    return true;
                }
			</script>
			
	<div class="shell">
		<div id="content">
	<div align="center"><h3><font color="DodgerBlue">Registration</font></h3></div>
    		<c:if test="${not empty servletMessage}"><font size="2" color="red">${servletMessage}</font></c:if><h4></h4>                         
			<table width="100%">
            <tr>
                <td width="700"><form action="${pageContext.request.contextPath}/register" method="post" name="form1" id="form1" onsubmit="return check()">
                        <table width ="110%" height="100" border="0" align="center">
                                                       
                            <tr height ="100%">
                                
                                <td width="6%" ><span class="style5"></span></td>
                                <td width="25%"><font size="2" color="black" face ="arial" >First Name  :&nbsp;</font><br></br></td>
                                <td width="45%"><input type="text" style="height:20px;font-size:10pt;" name="firstName" size="30" maxlength="30" value="${firstName}" /><br></br></td>
                                <td width="10%">&nbsp;</td>
                            </tr>
                            
                            <tr height = "100%">
                                <td><span class="style5"></span></td>
                                <td><font size="2" color="black" face ="arial" >Last Name  :&nbsp;</font><br></br></td>
                                <td><input type="text" style="height:20px;font-size:10pt;" name="lastName" size="30" maxlength="30" value="${lastName}"/><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                                                                                 
                            <tr height = "100%">
                                <td><span class="style5"></span></td>
                                <td><font size="2" color="black" face ="arial">Email Address  :</font><br></br></td>
                                <td><input type="text" style="height:20px;font-size:10pt;" name="email" size="30" maxlength="30" value="${email}" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height = "100%">
                                <td><span class="style5"></span></td>
                                <td><font size="2" color="black" face ="arial">Phone Number  :</font><br></br></td>
                                <td><input type="text" style="height:20px;font-size:10pt;" name="addrPhone" maxlength="15" size="15" value="${addrPhone}" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height = "100%">
                                <td><span class="style5"></span></td>
                                <td><font face="Arial, helvetica" color="black" size="-1">Street Address  :</font><br></br></td>
                                <td><input class="USS_TEXT" maxlength="30" style="height:20px;font-size:10pt;" name="addrDetails" size="30" value="${addrDetails}" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height = "100%">
                                <td><span class="style5"></span></td>
                                <td><font face="Arial, helvetica" color="black" size="-1">Province  :</font><br></br></td>
                                <td><input type="text" style="height:20px;font-size:10pt;" name="addrProvince" size="15" maxlength="15" value="${addrProvince}" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height = "100%">
                                <td><span class="style5"></span></td>
                                <td><font face="Arial, helvetica" color="black" size="-1">Postal Code  :</font><br></br></td>
                                <td><input class="USS_TEXT" maxlength="6" style="height:20px;font-size:10pt;" name="addrPostalCode" size="7" value="${addrPostalCode}" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height="100%">
                                <td></td>
                                <td><font size="2" color="black" face ="arial" >Country  :</font><br></br></td>
                                <td><input type="text" style="height:20px;font-size:10pt;" name="addrCountry" size="20" maxlength="30" value="${addrCountry}"/><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height ="100%">
                                <td><span class="style5"></span></td>
                                <td><font size="2" color="black" face ="arial">Username  :</font><br></br></td>
                                <td><input type="text" style="height:20px;font-size:10pt;" name="username" size="15" maxlength="10" value="" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height ="100%">
                                <td><span class="style5"></span></td>
                                <td><font size="2" color="black" face ="arial">Password  :</font><br></br></td>
                                <td><input type="password" style="height:20px;font-size:10pt;" name="password" size="15" maxlength="15" value="" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr height ="100%">
                                <td><span class="style5"></span></td>
                                <td><font size="2" color="black" face ="arial">Confirm Password  :</font><br></br></td>
                                <td><input type="password" style="height:20px;font-size:10pt;" name="cnfrmpassword" size="15" maxlength="15" value="" /><br></br></td>
                                <td>&nbsp;</td>
                            </tr>
                            
                            <tr>
                                <td colspan="4" align="center">
                                   <input type="reset" value="Reset" name="action" style="height:30px; width: 50px;font-size:10pt;"> 
                                   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                   <input type="submit" value="Register" name="action" style="height:30px; width: 60px;font-size:10pt;">
                                   <br /><br />
                                <br /> </td>
                            </tr>
                        </table>
                </form></td>
            </tr>
        </table>
	</div>
		<!-- End Content -->
	<div class="cl">
	</div>
</div>
<%@ include file="Footer.jsp" %>
</body>
</html>