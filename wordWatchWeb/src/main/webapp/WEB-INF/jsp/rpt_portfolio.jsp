<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Learning management - portfolio page</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.js"></script>
<script type="text/javascript" src="../css/main_rstech_backup.css"></script>
<script type="text/javascript" src="../script/jquery.slimscroll.js"></script>
<link id="CssLink" name="CssLink" rel="stylesheet"
	href="../css/rs_main.css" />
</head>
<body class="rs_main_background">
	<form name="FORM">
		<div class="rs_main_header">

			<table
				style="border-collapse: collapse; margin: 0; padding: 0; border: 0; width: 95%;">
				<tr>
					<td><span id="company_logo"
						style="text-align: center; margin-left: 10px"> <img
							src="../images/shared/Mastermind3.jpg" width="500" height="79" />
					</span></td>
					<td align="right" valign="top"><span id="company_signout"
						style="text-align: center; margin-left: 10px;"><a
							style="text-decoration: none; color: #FFFFFF" href="tbd">User
								Settings</a><span>&nbsp;&nbsp;</span><a
							style="text-decoration: none; color: #FFFFFF" href="tbd">Sign
								Out</a></span></td>
				</tr>
			</table>
		</div>
		<div class="rs_container">
			<h3>My Portfolio</h3>
			<div style="margin-left: 100px; margin-bottom: 20px">

				<table
					style="border-collapse: collapse; margin: 0; padding: 0; border: 0">
					<tr>
						<td
							style="padding: 0; margin: 0; border: 0; line-height: 30px; font-family: MS Reference Sans Serif, Arial, Helvetica, sans-serif; color: #000000; background-color: #FFFFFF; width: 150px; background: url(../images/shared/rs_button_selection_left_left.gif) no-repeat bottom left;"><span
							style="text-align: center; margin-left: 10px">By Article</span></td>
						<td
							style="padding: 0; margin: 0; border: 0; line-height: 30px; font-family: MS Reference Sans Serif, Arial, Helvetica, sans-serif; color: #000000; background-color: #4a4a4a; width: 120px; background: url(../images/shared/rs_button_selection_left_right.gif) no-repeat right;"><span
							style="text-align: center; margin-left: 10px" title="under construction">By Term</span></td>

					</tr>
				</table>
				<table
					style="border-collapse: collapse; margin: 0; padding: 0; border: 0">
				</table>
			</div>
			
			 <c:if test="${fn:length(studentLink) > 0}">  
  <a href="${studentLink}"><span >Students</span></a> 
</c:if>  
			
			
			<div id="vertical-scroll-container">
				<div id="header-container">
					<table class="portfolio_table" cellpadding="0" cellspacing="0"
						style="clear: both;">
						<thead>
							<tr>
								<th class="lft_col" style="width: 2%;">#</th>
								<th style="width: 23%;"><a href="${sortByDateLink}">Date Last Accessed <img src="${imgsvr}" width="12" height="14" style="vertical-align:middle"></a></th>
								<th style="width: 65%;">Title</th>
								
								<th class="rgt_col" style="width: 10%;">Action</th>
							</tr>
						</thead>
					</table>
				</div>
				<div id="vertical-scroll-area">
					<table class="portfolio_table" cellpadding="0" cellspacing="0"
						style="clear: both; display: block">
						<c:set var="d" value="1" />
						<c:forEach items="${user_reports}" var="report" varStatus="status">

							<tr>
								<td style="width: 2%;"><c:out value="${d}" /></td>
								<td style="width: 23%;">${report.firstRunDate}</td>
								<td style="width: 35%;"><a href="${report.viewLink}"><span
										id="EvenReportName">${report.reportName}</span></a></td>
								
								<td style="width: 10%;"><a id="" href="${report.viewLink}"><span
										id="">View</span></a></td>
							</tr>
							<c:set var="d" value="${d+1}" />
						</c:forEach>
						<tr>
							<td class="lastRowLeftCell"
								style="width: 2%; border: 0; height: 15px"></td>
							<td style="width: 23%"></td>
							<td style="width: 35%"></td>
							<td class="lastRowRightCell" style="width: 10%; border: 0">
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		function dla(obj)
		{
			if (obj.innerHTML=="Date Last Accessed (ASC)")
			{
				obj.innerHTML="Date Last Accessed (DSC)";
			}
			else
			{
				obj.innerHTML="Date Last Accessed (ASC)";
			}
		}


        $(function () {
            $('#vertical-scroll-area').slimScroll({
                height: '800px'
            });
        });
    </script>
	</form>
</body>
</html>
