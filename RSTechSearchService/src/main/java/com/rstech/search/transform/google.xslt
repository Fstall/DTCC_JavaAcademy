<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:rs="http://www.rstechsvc.com/ns" xmlns:java="java:com.rstech.search.UrlUtil">
	<xsl:output method="xml" encoding="iso-8859-1" indent="yes"
		cdata-section-elements="Html" />
		
	<xsl:param name="html" ></xsl:param>
	<xsl:param name="source" >google</xsl:param>
	<xsl:param name="baseUrl" >http://www.google.com</xsl:param>
	<xsl:param name="resolveUrls">true</xsl:param>
	
	<xsl:template match="/" xpath-default-namespace="http://www.w3.org/1999/xhtml">
		<RSTechResult xmlns="" xsi:noNamespaceSchemaLocation="rstechResult.xsd">
			<Source>
				<xsl:value-of select="$source" />
			</Source>
			<xsl:for-each select="//div[@id='ssb']">
				<xsl:variable name="for"
					select="replace(normalize-space(substring-after(substring-before(., 'for'), 'about')), ',', '')" />
				<xsl:variable name="linking"
					select="replace(normalize-space(substring-after(substring-before(., 'linking'), 'about')), ',', '')" />
				<xsl:choose>
					<xsl:when test="$for!=''">
						<NumberOfResults>
							<xsl:value-of select="$for" />
						</NumberOfResults>
					</xsl:when>
					<xsl:when test="$linking!=''">
						<NumberOfResults>
							<xsl:value-of select="$linking" />
						</NumberOfResults>
					</xsl:when>
					<xsl:otherwise>
						<NumberOfResults>0</NumberOfResults>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:for-each>
			
			<SponsoredResults>				
				<xsl:for-each select="//ol[@onmouseover='return true']/li/h3">
					<xsl:variable name="anchor" select=".//a" />
					<xsl:variable name="link" select="rs:getActualUrl($anchor/@href)" />
					<xsl:variable name="adUrl" select="$anchor/@href" />
					<xsl:if test="$link!=''">					
						<SponsoredResult>			
							<Title>
								<xsl:value-of select="normalize-space($anchor)" />
							</Title>						
							<Link>
								<xsl:value-of select="$link" />
							</Link>			
						    <AdUrl>
						    	<xsl:text>http://www.google.com</xsl:text><xsl:value-of select="$adUrl" />
						    </AdUrl>
							<!-- Snippet: all the text of the following siblings of h3 -->
							<xsl:variable name="snippet">							
								<xsl:for-each select="following-sibling::* | following-sibling::text()">
									<!-- show everything except the results occasionally nested in these elements -->
									<xsl:apply-templates/>
								</xsl:for-each>
							</xsl:variable>						
							<Snippet>
								<xsl:value-of select="substring(normalize-space($snippet), 0, 3000)" />
							</Snippet>
						
						</SponsoredResult>
					</xsl:if>				
				</xsl:for-each>				
			</SponsoredResults>
			
			<Results>
				<xsl:for-each select="//h3[@class='r']">

					<xsl:variable name="anchor" select=".//a" />
					<!-- title: the text value of the anchor element -->
					<xsl:variable name="title" select="normalize-space($anchor)" />
					<!-- link: the href attribute value of the anchor element -->
					<xsl:variable name="link" select="$anchor/@href" />

					<!-- Only create the result if the link is not null -->
					<xsl:if test="$link!=''">					
						<Result>			
							<Title>
								<xsl:value-of select="$title" />
							</Title>
						
							<Link>
								<xsl:value-of select="$link" />
							</Link>			
						
							<!-- Snippet: 
								- Snippet is usually included in the next div element, and
									only the portion before the nested br or div is needed.
								- No snippet, if the next div have an attribute class 
									with value 'e'. These results are usually book results
									including a number of links. 
								- No snippet, if the next element is table. These results are 
									usually news results, google book result, shopping results
									and etc. which include a number of links 
								- Sometimes, the snippet is in the next font element. Examples 
									are youtube.com results
						 	-->
							<xsl:variable name="snippet">							
								<xsl:for-each 
									select="following-sibling::div[position()=1 and @class!='e']/br[position()=1]/preceding-sibling::* | 
											following-sibling::div[position()=1 and @class!='e']/br[position()=1]/preceding-sibling::text() |
											following-sibling::div[position()=1 and @class!='e']/div[position()=1]/preceding-sibling::* |
											following-sibling::div[position()=1 and @class!='e']/div[position()=1]/preceding-sibling::text() |
											following-sibling::font[position()=1]/br[position()=1]/preceding-sibling::* |
											following-sibling::font[position()=1]/br[position()=1]/preceding-sibling::text()
											">								
									<!-- non-blank text node -->
									<xsl:if test="local-name(.)='' and normalize-space(.)!=''">
										<xsl:value-of select="'', concat(normalize-space(.), ' ')"/>
									</xsl:if>
									<!-- other desired nodes -->					
									<xsl:if
										test="local-name(.)!='' and local-name(ancestor)!='a' and local-name(.)!='span' and local-name(.)!='nobr' and local-name(.)!='a' and normalize-space(.)!='-' ">
										<xsl:value-of select="normalize-space(.)" />
									</xsl:if>								
								</xsl:for-each>
							</xsl:variable>						
							<Snippet>
								<xsl:value-of select="normalize-space($snippet)" />
							</Snippet>
						
						</Result>
					</xsl:if>
				</xsl:for-each>
			</Results>
			
			<MoreResultsLinks>
				<xsl:for-each select="//td/a[starts-with(@href, '/search?hl')]">
					<xsl:variable name="link" select="@href" />
					<xsl:if test="$link!=''">
						<MoreResultsLink>
							<xsl:value-of select="$baseUrl" />
							<xsl:value-of select="$link" />
						</MoreResultsLink>
					</xsl:if>
				</xsl:for-each>
			</MoreResultsLinks>
			<xsl:if test="$html!=''">
				<Html>
					<xsl:value-of select="$html" />
				</Html>
			</xsl:if>
		</RSTechResult>
	</xsl:template>	
	
	<!-- Sometimes, the results element is nested in the sponsored result element 
		 after JTidy processes the HTML. Make sure not to include them in the snippet 
		 of the sponsored results   	
	 -->
	<xsl:template match="id('res')">	
	</xsl:template>
	
	<xsl:function name="rs:getActualUrl">
		<xsl:param name="link" />
		<xsl:if test="$link!=''">
			<xsl:variable name="url">
				<xsl:if test="starts-with($link, '/')">
					<xsl:value-of select="$baseUrl" />
				</xsl:if>
				<xsl:value-of select="$link" />
			</xsl:variable>
			<xsl:value-of select="java:getActualUrl($url, $resolveUrls)" />
		</xsl:if>
	</xsl:function>
</xsl:stylesheet>
