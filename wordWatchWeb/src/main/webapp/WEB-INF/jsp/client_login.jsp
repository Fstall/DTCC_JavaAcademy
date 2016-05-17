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
                    <td style="width: 100%; border: 0; padding: 0; margin: 0;">
                        <div class="left-div">
                            <div id="tabs">
                                <ul>
                                <li class="current" id="li_tab1" onclick="tab('tab1')"><a href="#">About</a></li>
                                <li id="li_tab2" onclick="tab('tab2')"><a href="#">Creator Notes</a></li>
                                <li id="li_tab3" onclick="tab('tab3')"><a href="#">Recent News</a></li>
                                <li id="li_tab4" onclick="tab('tab4')"><a href="#">Tutorial</a></li>
                                <li id="li_tab5" onclick="tab('tab5')"><a href="#">Contact Us</a></li>
                                </ul>
                                <div id="Content_Area">
                                <div id="tab1">
                                <div id="tab1container">
                                <p><iframe width="640" height="360" src="http://www.youtube.com/embed/DVfl3yDd87w?rel=0" frameborder="0" allowfullscreen></iframe></p>
                                About mass.term.mind&#8482;:
<p>Ever feel overwhelmed by English vocabulary, particularly whenever you need to do some online reading? 
Despair no more! Let mass.term.mind help you organize your terminology as you read online, giving you definitions, images, videos, and more to assist you in understanding every English word you come across. 
mass.term.mind will be a knowledgeable teacher, your best friend, and a trustworthy companion throughout the pains of learning English, whether as a foreign language or as your native tongue. 
No longer will you need to create notecards, flip through dictionaries and thesauruses, or look up anything online by yourself, as mass.term.mind does it all for with one simple highlight of the word in question.
Try mass.term.mind today and never look back! If you have any questions or suggestions, feel free to contact us and let us know so that we can continue to improve this tool to fulfill the needs of all.</p>

                               
                                </div>
                                </div>
                                <div id="tab2" style="display: none;">
                                <div id="tab2container">
                                <p>When I began learning English, I remember coming across words that I didn't know and having to go to a dictionary, which likely only gave me more words to look up.
                                Before I knew it, I would be stuck in a chain of looking up words without context rather than learning from what I was reading.
                                This got me thinking: how can I make this process as painless as possible?
                                After main years of consideration, I came up with this tool so that others do not need to go through the same thing as I did.
                                No longer do you need to consult multiple sources before you finally figure out what a word means.
                                All you need to do is highlight it, and mass.term.mind takes you straight to all the information you need.
                                If you find yourself stuck, not knowing what a word in the definition means, mass.term.mind can help you find all the information you need for that word.
                                In this fast-paced world, everything is based on efficiency, and mass.term.mind will give you just that when learning English terminology.
                                I hope this tool will help you as you learn the English language by greatly reducing the time you spend writing down your new terms, looking them up, and putting all the information together in one place.
                                Take advantage of mass.term.mind and let it do all the work for you.</p>
                                <p>Sincerely,</p>
                                <p>Tony Chou</p>
                                </div>
                                </div>
                                <div id="tab3" style="display: none;">
                                <div id="tab3container">
                                February 15, 2013: mass.term.mind 0.01 up and running.<br/>
                                March 10, 2013: mass.term.mind Beta online!
                                </div>
                                </div>
                                <div id="tab4" style="display: none;">
                                <div id="tab4container">
                                <p>System Requirements:</p>
<p>Internet connection</p>
<p>Browser (at minimum IE to use the plugin)</p>
<p>System administrator privileges (so that you can download the plugin)</p>
<p></p>
<p>Currently, mass.term.mind is best if run on two different browsers, Internet Explorer 7 or above for the app (reading articles)
 and Mozilla Firefox for the website (due to some coding issues that still need to be worked out on other platforms).
 Although the website will work on IE, Chrome, and Safari, there are currently some stylistic bugs to the site on those browsers.
 You will also need administrator privileges on your computer in order to install the browser plugin.
 Currently, the website does not work on portable devices, and larger screen size (at least 1024X768 resolution)is recommended.</p>
 <p></p>
 <p>To use mass.term.mind, please do the following:</p>
                                <p>Download the browser plugin in IE (may expand to other browsers in the future).</p>
                                <p>Sign up for an account or sign in.</p>
                                <p>Open articles/websites that you are interested in reading from IE with the plugin active.</p>
                                <p>When you find a word that you do not know or just want to add to your vocabulary list, highlight the word (currently only single words may be highlighted).</p>
                                <p>This automatically sends the article information to mass.term.mind while initiating a search for the word, with results also saved to your account. 
                                You may now access the word, information about the word, and the article from your account.</p>
                                <p>Your account has 3 basic levels: user homepage with a list of articles you have read, article term page with a list of terms you selected in the article (accessed by clicking on "view" for the specific article you wish to see terms for), 
                                and the term page (accessed by clicking on "edit" for the term you wish to see details for).</p>
                                </div>
                                </div>
                                <div id="tab5" style="display: none;">
                                <div id="tab5container">
                                <p>If you have any questions, comments or suggestions, feel free to contact us at:</p>
                                <p>Email: mass.term.mind@gmail.com</p>
                                <p></p>
                                <p></p>
                                <p>Although this is currently a free service, we are  taking donations to further improve the functionality of this tool.</p>
                                </div>
                                </div>
                                </div>
                            </div>
                            </div>
                         </td>
                         <td valign="top"; align="right"; style="width: 225px; border: 0; padding: 0; margin: 0;">
                    <div class="right-div">
                            <table class="portfolio_table" style="background-color:inherit; border=0">
                                <tr>
                                    <td style="border:0">
                                        <div id="signin_table">
                                                <table style="border:0">
                                                <tr><td style="border:0; font-size:24px; color:#FFFFFF">
                                                Sign In
                                                </td></tr>
                                                <tr><td style="border:0">
                                                <label style="color:#FFFFFF">Username</label><input placeholder="Your email" name="User" type="text" id="User" style="width:200px; height: 30px"/>
                                                </td></tr>
                                                <tr>
                                                
                                                <td style="border:0">
                                                <label style="color:#FFFFFF">Password</label><input placeholder="Password" name="Password" type="password" id="Password" style="width:200px; height: 30px" />
                                                </td>
                                                
                                                
                                                </tr>
                                                <tr><td style="border:0; color:#FFFFFF""><input type="checkbox"/>Keep me signed in</td></tr>
                                                <tr><td style="border:0"> <a style="text-decoration: none; color:#FFFFFF" href="tbd">
                                                Forgot password? Click here.
                                                </a></td></tr>
                                                <tr><td style="border:0;width:15%">
                                                <input type="submit" name="event" id="Login" value="Sign In!" /></input>
                                                <a style="text-decoration: none; color:#FFFFFF" href="user_signup.do">&nbsp Not a user yet? Sign up now!</a></td>
                                                </tr>
                                                </table>
                                            </div>
                                            <tr>
                                    <td style="width: 35%; border: 0; padding: 0; margin: 0;">
                                    <img src="../images/shared/homepage_image.jpg" width="225" height="325"/>
                                    </td>
                                </tr>
                                <tr><td id="downloadapp"><a  style="text-decoration: none; color:#000000" href="../setup/RSSpeedDialSetup.msi">
                                Download App Here!
                                </a></td></tr>
                                            </table>
                                        </div>
                                    </td>
                                    </tr>
                                    
                            </table>
                </div>
        </div>
<script type="text/javascript">
    function tab(tab) {
        document.getElementById('tab1').style.display = 'none';
        document.getElementById('tab2').style.display = 'none';
        document.getElementById('tab3').style.display = 'none';
        document.getElementById('tab4').style.display = 'none';
        document.getElementById('tab5').style.display = 'none';
        document.getElementById('li_tab1').setAttribute("class", "");
        document.getElementById('li_tab2').setAttribute("class", "");
        document.getElementById('li_tab3').setAttribute("class", "");
        document.getElementById('li_tab4').setAttribute("class", "");
        document.getElementById('li_tab5').setAttribute("class", "");
        document.getElementById(tab).style.display = 'block';
        document.getElementById('li_' + tab).setAttribute("class", "active");
    }
    $(function () {
        $('#tab1container').slimScroll({
            height: '600px'
        });
    });
    $(function () {
        $('#tab2container').slimScroll({
            height: '600px'
        });
    });
    $(function () {
        $('#tab3container').slimScroll({
            height: '600px'
        });
    });
    $(function () {
        $('#tab4container').slimScroll({
            height: '600px'
        });
    });
    $(function () {
        $('#tab5container').slimScroll({
            height: '600px'
        });
    });
    $(function() {
        $('input, textarea').placeholder();
    });
</script>
</form>

</body>

</html>
