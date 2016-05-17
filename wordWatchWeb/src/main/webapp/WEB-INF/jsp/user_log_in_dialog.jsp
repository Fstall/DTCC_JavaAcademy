<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<title>User Login Dialog</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
<link href="css/main.css" rel="stylesheet" type="text/css">
<script src="js/vam.js" type="text/javascript"></script>
</head>

<body>
<jsp:useBean id="appError" class="com.rstech.wordwatch.web.ApplicationError" scope="page">
    <jsp:setProperty name="appError" property="errorIndicator" value="${error.errorIndicator}" />
</jsp:useBean>
<%
    if (appError.getErrorIndicator().equalsIgnoreCase("Y")) {
%>

<input type="hidden" name="documentApplicationError" id="documentApplicationError" value="${error.errorText} ${error.message}" />

<%  
    }
%>
<H1>
Please enter your log in information to MassTermMind:
</H1>
<!--Content box/tables  begin here-->
<div align=center>
<div id=Content>
<form>
<table cellpadding=0 cellspacing=10 border=0 width="100%" summary="">
                                                <tr><td style="border:0">
                                                <label style="color:#FFFFFF">Username</label><input placeholder="Your email" name="User" type="text" id="User" style="width:200px; height: 30px"/>
                                                </td></tr>
                                                <tr>
                                                
                                                <td style="border:0">
                                                <label style="color:#FFFFFF">Password</label><input placeholder="Password" name="Password" type="password" id="Password" style="width:200px; height: 30px" />
                                                </td>
  
<tr><td style="border:0;width:15%">
                                                <input type="submit" name="event" id="Login" value="Sign In!" /></input>
                                                <a style="text-decoration: none; color:#FFFFFF" href="user_signup.do">&nbsp Not a user yet? Sign up now!</a></td>
                                                </tr>
	  </table>
	  </form>
	</td>
  </tr>
</table>
</div>
</div>
</body>
<script>
function checkAlert()
{
	var element1 = document.getElementById("documentApplicationError");

	if(element1 != null)
	{
		alert(element1.value);
	}
	
} 
</script>

</html>