<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
	xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" encoding="iso-8859-1" indent="yes" cdata-section-elements="Html"/>

	<xsl:param name="html"/>
	<xsl:param name="source"/>
	<xsl:param name="baseUrl"/>
	<xsl:param name="resolveUrls"/>
	
	<xsl:template match="/" xpath-default-namespace="http://www.w3.org/1999/xhtml">
		<RSTechResult xmlns="">
			<Source><xsl:value-of select="$source"/></Source>
			<xsl:for-each select="//span[@id='count']">
				<xsl:variable name="number" select="replace(normalize-space(substring-before(substring-after(., 'of'), 'results')[1]), ',', '')"/>
				<xsl:if test="$number!=''">
					<NumberOfResults><xsl:value-of select="$number"/></NumberOfResults>
				</xsl:if>
			</xsl:for-each>
			
			<SponsoredResults>
				<xsl:for-each select="//div[@class='sb_adsW']/ul/li">
					<SponsoredResult>
						<xsl:variable name="adUrl" ><xsl:value-of select=".//@href" /></xsl:variable>
						<xsl:if test="normalize-space(h3[1])!=''">
							<Title><xsl:value-of select="normalize-space(h3[1])"/></Title>
						</xsl:if>
						<xsl:variable name="link" select="normalize-space(substring-after(cite, '- ')[1])"/>
						<Link><xsl:text>http://</xsl:text><xsl:value-of select="$link"/></Link>
					    <AdUrl>
					    	<xsl:value-of select="$adUrl" />
					    </AdUrl>
						<xsl:if test="normalize-space(p[1])!=''">
							<Snippet><xsl:value-of select="normalize-space(p[1])"/></Snippet>						
						</xsl:if>
					</SponsoredResult>		
				</xsl:for-each>
				
				<xsl:for-each select="//div[@class='sb_adsN']/ul/li">
					<SponsoredResult>
						<xsl:if test="normalize-space(h3[1])!=''">
							<Title><xsl:value-of select="normalize-space(h3[1])"/></Title>
						</xsl:if>
						<xsl:variable name="link" select="normalize-space(cite[1])"/>
						<xsl:variable name="adUrl" ><xsl:value-of select=".//@href" /></xsl:variable>
						<xsl:if test="$link!=''">
							<Link><xsl:text>http://</xsl:text><xsl:value-of select="$link"/></Link>
						</xsl:if>
					    <AdUrl>
					    	<xsl:value-of select="$adUrl" />
					    </AdUrl>
						<xsl:if test="normalize-space(p[1])!=''">
							<Snippet><xsl:value-of select="normalize-space(p[1])"/></Snippet>						
						</xsl:if>
					</SponsoredResult>		
				</xsl:for-each>	
				<xsl:for-each select="//div[@class='sb_adsW sb_adsW2']/ul/li">
					<SponsoredResult>
						<xsl:if test="normalize-space(h3[1])!=''">
							<Title><xsl:value-of select="normalize-space(h3[1])"/></Title>
						</xsl:if>
						<xsl:variable name="link" select="normalize-space(cite[1])"/>
						<xsl:variable name="adUrl" ><xsl:value-of select=".//@href" /></xsl:variable>
						<xsl:if test="$link!=''">
							<Link><xsl:text>http://</xsl:text><xsl:value-of select="$link"/></Link>
						</xsl:if>
					    <AdUrl>
					    	<xsl:value-of select="$adUrl" />
					    </AdUrl>
						<xsl:if test="normalize-space(p[1])!=''">
							<Snippet><xsl:value-of select="normalize-space(p[1])"/></Snippet>						
						</xsl:if>
					</SponsoredResult>		
				</xsl:for-each>	
				
			</SponsoredResults>
						
			<Results>
				<xsl:for-each select="//div[@id='results']/ul/li">
					<Result>
						<xsl:if test="normalize-space(div/div[@class='sb_tlst']/h3/a[1])!=''">
							<Title><xsl:value-of select="normalize-space(div/div[@class='sb_tlst']/h3/a[1])"/></Title>
						</xsl:if>
						<xsl:if test="div/div[@class='sb_tlst']/h3/a/@href!=''">
							<Link><xsl:value-of select="div/div[@class='sb_tlst']/h3/a/@href[1]"/></Link>
						</xsl:if>
						<xsl:variable name="snippet" select="div/p|div/div[2]"/>
						<xsl:if test="normalize-space($snippet[1])!=''">
							<Snippet><xsl:value-of select="normalize-space($snippet[1])"/></Snippet>						
						</xsl:if>
					</Result>		
				</xsl:for-each>		
			</Results>
			<MoreResultsLinks>
				<xsl:for-each select="//div[@class='sb_pag']//a[starts-with(@href, '/search?')]">
					<MoreResultsLink><xsl:value-of select="$baseUrl"/><xsl:value-of select="@href"></xsl:value-of></MoreResultsLink>			
				</xsl:for-each>
			</MoreResultsLinks>
			
			<xsl:if test="$html!=''">
				<Html><xsl:value-of select="$html"/></Html>
			</xsl:if>
		</RSTechResult>
	</xsl:template>
	
</xsl:stylesheet>
