<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Learning management - list page</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.js"></script>
    <script type="text/javascript" src="../css/main_rstech_backup.css"></script>
    <script type="text/javascript" src="../script/jquery.slimscroll.js"></script>
    <link id="CssLink" name="CssLink" rel="stylesheet" href="../css/rs_main.css" />  
</head>
<body class="rs_main_background">
    <div class="rs_main_header"> 
        <input type="hidden" id="page" name="page" value="">
        
        <table style="border-collapse:collapse ;margin: 0; padding:0; border:0; width: 95%;">
        <tr>
       <td>
                    <span id="company_logo" style="text-align: center; margin-left: 10px">
                    
                    <img src="../images/shared/Mastermind3.jpg" width="500" height="79"/>
                    </span>
                </td>
                <td align="right" valign="top">
                    <span id="company_signout" style="text-align: center; margin-left: 10px"><a style="text-decoration: none; color:#FFFFFF" href="tbd">User Settings</a><span>&nbsp;&nbsp;</span><a
                        style="text-decoration: none; color:#FFFFFF" href="tbd">Sign Out</a></span>
                </td>
        </tr>
        </table>
    

    </div>
    <div class="rs_container">
        <h3>Term List</h3>
        <form>
        <div style="margin-left: 20px;margin-bottom:20px">

        <table style="border-collapse:collapse ;margin: 0; padding:0; border:0; width: 100%">
        <tr>
        <td style="padding:0;margin:0;border: 0;line-height: 30px;font-family: MS Reference Sans Serif, Arial, Helvetica, sans-serif;color: #000000;background-color:#FFFFFF;width:80%;background: url(file:///C:/projects/java/rstech/app/wordWatchWeb/src/main/webapp/images/shared/rs_article_title.gif) no-repeat bottom left;"><span style="text-align:center;margin-left:10px"><a href="${report.viewLink}" style="text-decoration: none; color:#FFFFFF">Source article name here</a></span></td>
        <td style="padding:0;margin:0;border: 0;line-height: 30px;font-family: MS Reference Sans Serif, Arial, Helvetica, sans-serif;color: #000000;width:20%;"  align="right"><span style="text-align:center;margin-left:10px"><a href="tbd" style="text-decoration: none; color:#FFFFFF">Back</a><span>&nbsp;&nbsp;</span></td>

        </tr>
        </table>

        </div>
        <div id="vertical-scroll-container">
            <div id="header-container"> 
                    <table class="portfolio_table" cellpadding="0" cellspacing="0"  style="clear: both;">
                        <thead>
                            <tr>
                                <th class="lft_col" style="width: 5%;">
                                    #
                                </th>
                                <th style="width: 20%;">
                                    Term
                                </th>
                                <th style="width: 35%;">
                                    Definition
                                </th>
                                <th style="width: 20%;">
                                    Image
                                </th>
                                <th style="width: 10%;">
                                    Date
                                </th>
                                <th class="rgt_col" style="width: 10%;">
                                    Action
                                </th>
                            </tr>
                        </thead>
                    </table>
                </div>
                <div id="vertical-scroll-area"> 
                    <table class="portfolio_table" cellpadding="0" cellspacing="0"   style="clear: both;">
                        
                        <%
	 int index;
     index = 0;
	 %>
	<c:set var="d" value="1" />
	<c:set var="imgUrl" value="${imgsvr}" />    
    <c:forEach items= "${words}" 
				 	var="eachWordEntry" 
				 	varStatus="status">
                        
                          
                        <tr>
                            <td style="width: 5%;">
                                <span id="EvenNum"><c:out value="${d}" /></span>.&nbsp;&nbsp;
                            </td>
                            <td style="width: 20%;">
                                ${eachWordEntry.WORD}
                            </td>
                            <td style="width: 35%;">
                                ${eachWordEntry.DEFINITION}
                            </td>
                            <td style="width: 20%;">
                                <img src="${imgUrl}${eachWordEntry.IMG_URL3}"></img>
                            </td>
                            <td style="width: 10%;">
                                ${eachWordEntry.ENTRY_PUBLISHED_DATE}
                            </td>
                            <td style="width: 10%;">
                                <a href="${word_edit_links[status.index]}" id="EvenEditLink"><span id="Span2">Edit</span></a>&nbsp;&nbsp<a href="tbd"><span id="Span3">Delete</span></a>
                            </td>
                        </tr>
                         <%
	 
	index ++;
	%>
	<c:set var="d" value="${d+1}" />   
    </c:forEach>
                      <tr>
                                                    <td class="lastRowLeftCell" style="width: 5%; height: 15px; border: 0"></td>
                            						<td style="width:20%"></td>
                            						<td style="width: 35%"></td>
                            						<td style="width:20%"></td>
                            						<td style="width:10%"></td>
                                                    <td class="lastRowRightCell" style="width: 20%; border:0">
                                                    
                                                    </td>
                                                </tr>
                    </table>
                </div>
            
        </div>
    </div>
    </form>
    <script type="text/javascript">

        $(function () {
            $('#vertical-scroll-area').slimScroll({
                height: 'auto'
            });
        });
    </script>
</body>
</html>
