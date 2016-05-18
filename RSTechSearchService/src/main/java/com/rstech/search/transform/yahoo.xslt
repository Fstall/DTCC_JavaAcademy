<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:np="http://www.rstechsvc.com/ns"
	xmlns:java="java:com.rstech.search.UrlUtil">

	<xsl:output method="xml" encoding="iso-8859-1" indent="yes" cdata-section-elements="Html"/>

	<xsl:param name="html"/>
	<xsl:param name="source"/>
	<xsl:param name="baseUrl"/>
	<xsl:param name="resolveUrls"/>
	
	<xsl:template match="/" xpath-default-namespace="http://www.w3.org/1999/xhtml" >
		<RSTechResult xmlns="">
			<Source><xsl:value-of select="$source"/></Source>
			<xsl:for-each select="//span[@id='infotext']">
				<xsl:variable name="number" >
					<xsl:choose>
						<xsl:when test="contains(., 'of about')">
							<xsl:value-of select="replace(normalize-space(substring-after(substring-before(., 'for'), 'of about')), ',', '')"/>
						</xsl:when>
						<xsl:otherwise>
							<xsl:value-of select="replace(normalize-space(substring-after(substring-before(., 'for'), 'of ')), ',', '')"/>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:variable>
				<xsl:if test="$number!=''">
					<NumberOfResults><xsl:value-of select="$number"/></NumberOfResults>
				</xsl:if>
			</xsl:for-each>
			<SponsoredResults>
				<xsl:for-each select="//div[@class='spns']//li">
					<SponsoredResult>						
						<xsl:variable name="title" select="normalize-space((.//div/a)[1])"/>
						<xsl:if test="$title!=''">
							<Title><xsl:value-of select="$title"/></Title>
						</xsl:if>
						<xsl:variable name="href" select="div/a/@href"/>
						<xsl:variable name="url" select="div/em"/>
						<xsl:variable name="link" select="rs:getActualUrl($href,$url)"/>
						<xsl:variable name="adUrl" select="$href" />
						<xsl:if test="$link!=''">
							<Link><xsl:value-of select="$link"/></Link>
						</xsl:if>
					    <AdUrl>
					    	<xsl:value-of select="$adUrl" />
					    </AdUrl>
						<xsl:variable name="snippet" select="normalize-space(div[1])"/>
						<xsl:if test="$snippet!=''">
							<Snippet><xsl:value-of select="normalize-space($snippet)"/></Snippet>
						</xsl:if>
					</SponsoredResult>		
				</xsl:for-each>
				<!-- The following section deals with sponsored results on the right hand side of the page. Unfortunately, it is mostly the same as the above code. -->
				
			         <xsl:for-each select="//div[@id='east']/ul/li">
						<xsl:variable name="y_msg" select="normalize-space(a[1])"/>
						<xsl:if test="$y_msg != 'See your message here...'">
							 <SponsoredResult>
								<xsl:variable name="title_format_A" select="normalize-space((div[@class='title']/a)[1])"/>
								<!-- tchou note - as of 9/25, some sponsor results have title div in it, some don't, hence the extra work -->
								<xsl:variable name="title_format_B" select="normalize-space(a[1])"/> 
								<xsl:variable name="title_format_C" select="a[1]/strong"/> 
								<xsl:choose>
									<xsl:when test="$title_format_A != '' " >   <!-- test to see if that title format is not empty --> 
										<Title><xsl:value-of select="$title_format_A"/></Title>
									</xsl:when>
									<xsl:when test="$title_format_B !='' " >  <!-- else test to see if the other title format is not empty --> 
										<Title><xsl:value-of select="$title_format_B"/></Title>
									</xsl:when>
									<xsl:when test="$title_format_C !='' " >  <!-- else test to see if the other title format is not empty --> 
										<Title><xsl:value-of select="$title_format_C"/></Title>
									</xsl:when>
									<xsl:otherwise>  <!-- default case --> 
										<Title>Broken Title</Title>
									</xsl:otherwise> 
								</xsl:choose>
								<xsl:variable name="href" select="div/a/@href"/>
								<xsl:variable name="url" select="em"/>
								<xsl:variable name="link" select="rs:getActualUrl($href,$url)"/>
								<xsl:variable name="adUrl" select="a/@href"/>
								<xsl:if test="$link!=''">
									<Link><xsl:value-of select="$link"/></Link>
								</xsl:if>
								<AdUrl>
									<xsl:value-of select="$adUrl"/>
								</AdUrl>
								<xsl:variable name="snippet" select="normalize-space(div[1])"/>
								<xsl:if test="$snippet!=''">
									<Snippet><xsl:value-of select="normalize-space($snippet)"/></Snippet>
								</xsl:if>
							 </SponsoredResult>
						 </xsl:if>
					 </xsl:for-each> 
			</SponsoredResults>
			<Results>
				<!-- The expression //div[@id='web']//li/div[starts-with(@id,'sm-')] matches wikipedia results. 
					Examples can be found in the search page for "rent" and "hotel" -->
				<xsl:for-each select="//div[@id='web']//li//div[@class='res'] | //div[@id='web']//li//div[@class='res indent'] | //div[@id='web']//li/div[starts-with(@id,'sm-')]">
					<Result>
						<xsl:variable name="title" select="normalize-space(div/h3/a[1])"/>
						<xsl:if test="$title!=''">
							<Title><xsl:value-of select="$title"/></Title>
						</xsl:if>
						<xsl:variable name="href" select="div/h3/a/@href"/>
						<xsl:variable name="url" select=".//span[@class='url'][1]"/>
						<xsl:variable name="link" select="rs:getActualUrl($href,$url)"/>
						<xsl:if test="$link!=''">
							<Link><xsl:value-of select="$link"/></Link>
						</xsl:if>
						<!-- The expression .//div[@class='sm-abs'][1] matches snippet for wikipedia results. -->
						<xsl:variable name="snippet" select="normalize-space(div[@class='abstr'][1] | .//div[@class='sm-abs'][1])"/>
						<xsl:if test="$snippet!=''">
							<Snippet><xsl:value-of select="normalize-space($snippet)"/></Snippet>
						</xsl:if>
					</Result>		
				</xsl:for-each>		
			</Results>
			<MoreResultsLinks>
				<xsl:for-each select="//div[@id='pg']/a">
					<MoreResultsLink><xsl:value-of select="@href"></xsl:value-of></MoreResultsLink>			
				</xsl:for-each>
			</MoreResultsLinks>
			<xsl:if test="$html!=''">
				<Html><xsl:value-of select="$html"/></Html>
			</xsl:if>
		</RSTechResult>
	</xsl:template>
	
	<xsl:function name="np:getActualUrl">
		<xsl:param name="href"/>
		<xsl:param name="link"/>
		<xsl:if test="$link!=''">
			<xsl:variable name="link2" select="replace(normalize-space($link),' ','')"/>
			<xsl:if test="$link2!=''">
				<xsl:choose>
					<xsl:when test="contains($link2, '...')">
					<xsl:value-of select="java:getActualUrl($href, $resolveUrls)"/>

						<!-- tchou commented out this line... uncomment it if you are running in XMLSpy because it would not understand the namespace jar file when you debug in it.  
						<xsl:value-of select="BBBBBBBB"/>
                  -->   
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>http://</xsl:text><xsl:value-of select="$link2"/>					
					</xsl:otherwise>
				</xsl:choose>			
			</xsl:if>				
		</xsl:if>
	</xsl:function>
	
</xsl:stylesheet>
