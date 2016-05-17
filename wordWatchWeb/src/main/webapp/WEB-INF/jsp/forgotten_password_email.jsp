<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Learning management - detail page</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.js"></script>
    <script type="text/javascript" src="../css/main_rstech_backup.css"></script>
    <script type="text/javascript" src="../script/jquery.slimscroll.js"></script>
    <script type="text/javascript" src="../script/jquery.placeholder.js"></script>
    <link id="CssLink" name="CssLink" rel="stylesheet" href="../css/rs_main.css" /> 
</head>

<style type="text/css">
#tabs ul {
padding: 0px;
margin: 0px;
list-style-type: none;
}

#tabs ul li {
display: inline-block;
clear: none;
float: left;
height: 24px;
}

#tabs ul li a {
position: relative;
margin-top: 16px;
display: block;
margin-left: 6px;
line-height: 24px;
padding-left: 10px;
background: #969696;
z-index: 9999;
border: 1px solid #000000;
border-bottom: 0px;
-webkit-border-top-left-radius: 10px;
-webkit-border-top-right-radius: 10px;
-moz-border-top-left-radius: 10px;
-moz-border-top-right-radius: 10px;
border-top-left-radius: 10px;
border-top-right-radius: 10px;
width: 130px;
color: #000000;
text-decoration: none;
font-weight: bold;
behavior: url(C:/Users/Tony/Downloads/PIE-1.0.0/PIE.htc);
}
#tabs ul li.current a: active
{
background-color:#FFFFFF;
	}
#tabs ul li a:hover 
{
background-color:#BEBEBE;
}

#tabs ul li a:active
{
	background-color:#FFFFFF;
	}

#tabs #Content_Area {
padding: 0 15px;
clear:both;
overflow:hidden;
line-height:19px;
position: relative;
top: 20px;
z-index: 5;
height: 150px;
overflow: hidden;
height:600px;
display:block;
background-color:#FFFFFF;
-webkit-border-radius: 15px;
-moz-border-radius: 15px;
border-radius: 15px;
behavior: url(C:/Users/Tony/Downloads/PIE-1.0.0/PIE.htc);
}

input#User
{
-webkit-border-radius: 5px;
-moz-border-radius: 5px;
border-radius: 5px;
behavior: url(C:/Users/Tony/Downloads/PIE-1.0.0/PIE.htc);
	}

input#Password
{
-webkit-border-radius: 5px;
-moz-border-radius: 5px;
border-radius: 5px;
behavior: url(C:/Users/Tony/Downloads/PIE-1.0.0/PIE.htc);
	}
td#downloadapp
{
width:150px;
height:70px;
-webkit-border-radius: 10px;
-moz-border-radius: 10px;
border-radius: 10px;
background: #FFFFFF;
text-align:center;
behavior: url(C:/Users/Tony/Downloads/PIE-1.0.0/PIE.htc);
 }
</style>

 
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

<body class="rs_main_background" onload="checkAlert();">>
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

<form name="Form" method="post" action="client_login.do">
    <div class="rs_main_header">
        <table style="border-collapse: collapse; margin: 0; padding: 0; border: 0; width: 95%;">
            <tr>
                <td>
                    <span id="company_logo" style="text-align: center; margin-left: 10px">
                    
                    <img src="../images/shared/Mastermind3.jpg" width="500" height="79"/>
                    </span>
                </td>
            </tr>
        </table>
    </div>
    <div class="rs_container">
        <div class="vertical_scroll_container">
            <table class="portfolio_table" style="clear: both;background: #3e4659;">
                <tr>
                            <table class="portfolio_table" style="background-color:inherit; border=0">
                                <tr>
                                    <td style="border:0">
                                        <div id="signin_table">
                                                <table style="border:0">
                                                <tr>
	                                                <td style="border:0; font-size:24px; color:#FFFFFF">
	                                                Password Assistance
	                                                </td>
                                                </tr>
                                                
                                                <tr>
	                                            <td style="border:0">
                                                <label style="color:#FFFFFF">Enter Your Email Address</label><input placeholder="Email" name="Email" id="Email" style="width:200px; height: 30px" />
                                                </td>
                                                <input type="submit" name="event" id="Reset" value="Reset" /></input>
                                                </tr>
                                                </table>
                                            </div>
                                            </table>
                                    </tr>
                                    
                            </table>
                </div>
        </div>
</form>

</body>

</html>
