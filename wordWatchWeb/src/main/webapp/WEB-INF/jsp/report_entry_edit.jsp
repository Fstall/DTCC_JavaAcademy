<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Learning management - detail page</title>
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
<body class="rs_main_background" >
	<div class="rs_main_header">
		<table
			style="border-collapse: collapse; margin: 0; padding: 0; border: 0; width: 95%;">
			<tr>
				<td><span id="company_logo"
					style="text-align: center; margin-left: 10px"> <img
						src="../images/shared/Mastermind3.jpg" width="500" height="79" />
				</span></td>
				<td align="right" valign="top">
					<div
						style="border-collapse: collapse; border: 0; padding: 0; margin: 0">
						<span
							style="font-family: MS Reference Sans Serif, Arial, Helvetica, sans-serif; font-size: 16"><a
							style="text-decoration: none; color: #FFFFFF;" href="">User
								Settings</a><span>&nbsp;&nbsp;</span><a
							style="text-decoration: none; color: #FFFFFF" href="">Sign
								Out</a></span>
					</div>
					<div>&nbsp</div>
					<div>
						<span
							style="font-family: MS Reference Sans Serif, Arial, Helvetica, sans-serif; font-size: 16"><a
							style="text-decoration: none; color: #FFFFFF" href="tbd">Back</a><span>&nbsp;&nbsp;&nbsp;</span>
							<span
							style="padding: 0; margin: 0; border: 0; line-height: 30px; font-family: MS Reference Sans Serif, Arial, Helvetica, sans-serif; color: #000000; background-color: #21394E; width: 100px; background: url(file:///C:/projects/java/rstech/app/wordWatchWeb/src/main/webapp/images/shared/rs_word_learned.gif) no-repeat bottom left;"><a
								style="text-decoration: none; color: #FFFFFF" href="tbd">&nbsp;Word
									Learned&nbsp</a></span></span>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<form name="Form" method="post" action="report_entry_edit.do">
	<div class="rs_container">


		<c:set var="theWord" value="${detailWord.theEntry}" />
		<c:set var="sampleSentence"
			value="${detailWord.sampleSentenceList[0].AUX_VALUE}" />
		<c:set var="index" value="0" />
		<c:set var="imgUrl" value="${imgsvr}" />
		<c:set var="endLoop" value="0" />
		<c:if test="${fn:length(detailWord.sampleSentenceList) > 0}">
			<c:set var="endLoop"
				value="${fn:length(detailWord.sampleSentenceList)}" />
		</c:if>

		<table class="detail_table" cellpadding="0" cellspacing="0"
			style="clear: both; background: #3e4659;">
			<tr>
				<td style="width: 50%; border: 0; padding: 0; margin: 0">
					<div id="left-div">
						<table id="topLeftTable" style="width: 100%">
							<tr>
								<td style="border: 0">
									<div class="general-div">
										<div class="general-div-header-container">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<thead>
													<tr>
														<th class="lft_col" align="left" ; style="width: 50%;">
															<div style="overflow: hidden; height: 30px;">Term:</div>
														</th>
														<th class="rgt_col" style="width: 50%;">
															<div
																style="overflow: hidden; height: 30px; text-align: right;">Sound</div>

														</th>
													</tr>
												</thead>
											</table>
										</div>
										<div class="general-div-vertical-scroll-area">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<tr>
													<td class="lastRowLeftCell"
														style="width: 80%; border-left: 0; border-bottom: 0; padding: 0 17px 0">
														<span> <img src="${imgUrl}${word.WORD}_40"></img>


													</span>
													</td>
													<td class="lastRowRightCell"
														style="border-right: 0; border-bottom: 0"></td>
												</tr>
											</table>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td style="border: 0">

									<div class="general-div">
										<div class="general-div-header-container">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<thead>
													<tr>
														<th class="lft_col" align="left" ; style="width: 90%;">

															<div style="overflow: hidden; height: 30px;">
																Definition (Courtesy of <a
																	href="http://www.pearsonlongman.com/">Longman</a>)
															</div>
														</th>
														<th class="rgt_col" style="width: 10%;"></th>
													</tr>
												</thead>
											</table>
										</div>
										<div id="left-div-definition-vertical-scroll-area">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<tr>
													<td align="left" ; valign="top"
														; style="width: 100%; height: 200px; border-bottom: 0;">
														<div style="overflow: hidden; padding: 0 17px 0">${word.DEFINITION}</div>

													</td>
												</tr>
											</table>
										</div>
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<tr>
												<td class="lastRowLeftCell"
													style="width: 50%; height: 15px; border: 0"></td>
												<td class="lastRowRightCell" style="width: 50%; border: 0"></td>
											</tr>
										</table>
									</div>


								</td>
							</tr>
							<tr>
								<td style="border: 0">
									<div class="general-div">
										<div class="general-div-header-container">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<thead>
													<tr>
														<th class="lft_col" align="left" ; style="width: 30%;">
															<div style="overflow: hidden; height: 30px;">Date
																first selected:</div>
														</th>
														<th class="rgt_col" align="left" ; style="width: 70%;">
															Article title:</th>
													</tr>
												</thead>
											</table>
										</div>
										<div id="date-div-vertical-scroll-area">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<tr>
													<td align="left" ; valign="top"
														; style="width: 30%; height: 80px; border-bottom: 0; padding: 0 17px 0">
														${word.ENTRY_PUBLISHED_DATE}</td>
													<td align="left" ; valign="top"
														; style="width: 70%; border-bottom: 0; padding: 0 17px 0">
														<a href="${report.RPT_URL}" target="_blank">${report.TITLE}</a>
													</td>
												</tr>
											</table>
										</div>
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<tr>
												<td class="lastRowLeftCell"
													style="width: 30%; height: 15px; border-top: 0; border-bottom: 0; border-left: 0"></td>
												<td class="lastRowRightCell"
													style="width: 70%; border-top: 0; border-bottom: 0; border-right: 0"></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td style="width: 50%; border: 0; padding-top: 0; margin: 0">



					<div id="right-div">
						<table id="topRightTable" style="width: 100%">
							<tr>
								<td style="border: 0">
									<div class="general-div-header-container">
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<thead>
												<tr>
													<th class="lft_col" align="left" ; style="width: 50%;">
														Sample sentences:</th>
													<th class="rgt_col" align="left" ; style="width: 50%;">
													</th>
												</tr>
											</thead>
										</table>
									</div>
									<div id="right-div-sample-sentence-vertical-scroll-area">
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<tr>
												<td align="left" ; valign="top"
													; style="width: 100%; height: 442px; border-bottom: 0; padding: 0 17px 0">



													<c:forEach items="${detailWord.sampleSentenceList}"
														var="each" varStatus="index" begin="0" end="${endLoop}">
														<p>${each.AUX_VALUE}</p>
													</c:forEach>


												</td>
											</tr>
										</table>
									</div>
									<table class="detail_table" cellpadding="0" cellspacing="0"
										style="clear: both;">
										<tr>
											<td class="lastRowLeftCell"
												style="width: 50%; height: 15px; border: 0"></td>
											<td class="lastRowRightCell" style="width: 50%; border: 0"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</div>




				</td>
			</tr>
		</table>
		<table class="detail_table" cellpadding="0" cellspacing="0"
			style="clear: both; background: #3e4659;">
			<tr>
				<td style="width: 50%; border: 0; padding: 0; margin: 0">

					<div id="left-div">
						<table id="prefixTable" style="width: 100%">
							<tr>
								<td style="border: 0">
									<div class="general-div">
										<div class="general-div-header-container">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<thead>
													<tr>
														<th class="lft_col" align="left" ; style="width: 50%;">
															Additional information</th>
														<th class="rgt_col" style="width: 50%;"></th>
													</tr>
												</thead>
											</table>
										</div>
										<div id="left-div-prefix-vertical-scroll-area">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<tr>
													<td align="left" ; valign="top"
														; style="width: 100%; height: 445px; border-bottom: 0; padding: 0 17px 0">
														<p>Prefixes:</p>

														<p>Suffixes:</p>

														<p>Word Origin:</p>
														<p>Synonyms:</p>
														<p>Antonyms:</p>
													</td>
												</tr>
											</table>

										</div>
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<tr>
												<td class="lastRowLeftCell"
													style="width: 50%; height: 15px; border: 0"></td>
												<td class="lastRowRightCell" style="width: 50%; border: 0"></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</td>
				<td style="border: 0">
					<div id="right-div">
						<table id="picturetable" style="width: 100%">
							<tr>
								<td style="border: 0">
									<div class="general-div-header-container">
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<thead>
												<tr>
													<th class="lft_col" align="left" ; style="width: 50%;">
														Pictures:</th>
													<th class="rgt_col" style="width: 50%;"></th>
												</tr>
											</thead>
										</table>
									</div>
									<div id="right-div-picture-vertical-scroll-area">
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<tr>
												<td align="left" ; valign="top"
													; style="width: 100%; height: 445px; border-bottom: 0; padding: 0 17px 0">
													<img src="${imgUrl}${eachWord.IMG_URL1}"
													style="max-height: 150px; max-width: 150px;"></img><br />
													<img src="${word.IMG_URL2}"
													style="max-height: 150px; max-width: 150px;"></img><br />
													<img src="${word.IMG_URL3}"
													style="max-height: 150px; max-width: 150px;"></img><br />
												</td>
											</tr>
										</table>
									</div>
									<table class="detail_table" cellpadding="0" cellspacing="0"
										style="clear: both;">
										<tr>
											<td class="lastRowLeftCell"
												style="width: 50%; height: 15px; border: 0"></td>
											<td class="lastRowRightCell" style="width: 50%; border: 0"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</div>

				</td>


			</tr>
		</table>
		<table class="detail_table" cellpadding="0" cellspacing="0"
			style="clear: both; background: #3e4659;">
			<tr>
				<td style="width: 50%; border: 0; padding: 0; margin: 0">

					<div class="left-div">
						<table id="Table2" style="width: 100%">
							<tr>
								<td style="border: 0">
									<div class="general-div">
										<div class="general-div-header-container">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<thead>
													<tr>
														<th class="lft_col" align="left" ; style="width: 50%;">
															Additional tools</th>
														<th class="rgt_col" style="width: 50%;"></th>
													</tr>
												</thead>
											</table>
										</div>
										<div id="Div1">
											<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<tr>
													<td align="left" ; valign="top"
														; style="width: 100%; height: 249px; border-bottom: 0; padding: 0 17px 0">
														<p>Youku:</p>
														<p>Sogou:</p>
														<p>
													</td>

												</tr>
											</table>
										</div>
										<table class="detail_table" cellpadding="0" cellspacing="0"
											style="clear: both;">
											<tr>
												<td class="lastRowLeftCell"
													style="width: 50%; height: 15px; border: 0"></td>
												<td class="lastRowRightCell" style="width: 50%; border: 0"></td>
											</tr>
										</table>








										<div class="general-div">
											<div class="general-div-header-container">
												<table class="detail_table" cellpadding="0" cellspacing="0"
													style="clear: both;">
													<thead>
														<tr>
															<th class="lft_col" align="left" ; style="width: 90%;">
																<div style="overflow: hidden; height: 30px;">Write
																	your notes here</div>
															</th>
															<th class="rgt_col" style="width: 10%;"></th>
														</tr>
													</thead>
												</table>
											</div>
											<div id="left-div-note-vertical-scroll-area">
												<table class="detail_table" cellpadding="0" cellspacing="0"
													style="clear: both;">
													<tr>
														<td align="left" ; valign="top"
															; style="width: 100%; height: 100px; border-bottom: 0;">

															<textarea maxlength="2000" class="textbox" placeholder="Write your notes here...(max 400 words)" rows="5" cols="1" name="notes"></textarea>
															<input type="hidden" value="${entry_id}" name="entry_id">
															<input type="submit" value="save" name="save">
														</td>
													</tr>
												</table>
												<table class="detail_table" cellpadding="0" cellspacing="0"
												style="clear: both;">
												<tr>
													<td class="lastRowLeftCell"
														style="width: 50%; height: 15px; border: 0"></td>
													<td class="lastRowRightCell" style="width: 50%; border: 0"></td>
												</tr>
											</table>
											</div>
											
											
										</div>
								</td>
							</tr>
						</table>

					</div>


				</td>
				<td style="width: 50%; border: 0; padding: 0; margin: 0">
					<div id="right-div-video">
						<div id="div-video-header-container">
							<table class="detail_table" cellpadding="0" cellspacing="0"
								style="clear: both;">
								<thead>
									<tr>
										<th class="lft_col" align="left" ; style="width: 50%;">
											Videos:</th>
										<th class="rgt_col" style="width: 50%;"></th>
									</tr>
								</thead>
							</table>
						</div>
						<div id="right-div-video-vertical-scroll-area"
							style="height: 500px;">
							<table class="detail_table" cellpadding="0" cellspacing="0"
								style="clear: both;">
								<tr>
									<td align="left" valign="top"
										style="width: 100%; height: 455px; border-bottom: 0; padding: 0 17px 0">
										with variables and youtube links</td>
								</tr>
							</table>
						</div>
						<table class="detail_table" cellpadding="0" cellspacing="0"
							style="clear: both;">
							<tr>
								<td class="lastRowLeftCell"
									style="width: 50%; height: 15px; border: 0"></td>
								<td class="lastRowRightCell" style="width: 50%; border: 0"></td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>

		</tr>
		</table>
	</div>
	</form>
	
	<script type="text/javascript">
		function hiddenURL()
		{
			var entryID = document.getElementsByName("entry_id");
			}
		$(function() {
			$('#date-div-vertical-scroll-area').slimScroll({
				height : '80px'
			});
		});

		$(function() {
			$('#left-div-definition-vertical-scroll-area').slimScroll({
				height : '200px'
			});
		});

		$(function() {
			$('#right-div-video-vertical-scroll-area').slimScroll({
				height : '455px'
			});
		});

		$(function() {
			$('#right-div-sample-sentence-vertical-scroll-area').slimScroll({
				height : '442px'
			});
		});

		$(function() {
			$('#left-div-prefix-vertical-scroll-area').slimScroll({
				height : '445px'
			});
		});
		$(function() {
			$('#right-div-picture-vertical-scroll-area').slimScroll({
				height : '445px'
			});
		});

		$(function() {
			$('#left-div-tools-vertical-scroll-area').slimScroll({
				height : '500px'
			});
		});
	</script>
</body>
</html>
