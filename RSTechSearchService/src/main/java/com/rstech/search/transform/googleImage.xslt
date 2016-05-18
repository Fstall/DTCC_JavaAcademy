<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns="http://www.w3.org/1999/xhtml"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:rs="http://www.rstechsvc.com/ns" xmlns:java="java:com.rstech.search.UrlUtil">
	<xsl:output method="xml" encoding="iso-8859-1" indent="yes"
		cdata-section-elements="Html" />
		
	<xsl:param name="html" ></xsl:param>
	<xsl:param name="source" >GOOGLE_IMAGE</xsl:param>
	<xsl:param name="baseUrl" >http://image.google.com</xsl:param>
	<xsl:param name="resolveUrls">true</xsl:param>
	<xsl:param name="image_tables"></xsl:param>
	
	<xsl:template match="/" xpath-default-namespace="http://www.w3.org/1999/xhtml">
		<RSTechResult xmlns="" xsi:noNamespaceSchemaLocation="rstechResult.xsd">
			<Source>
				 <xsl:value-of select="$source" />
			</Source>   
			<Results>
			<xsl:variable name="image_tables" select="//div[@id='rg_s']" /> 
				<xsl:for-each select="//div[@class='rg_di']">
				<xsl:variable name="Item" select="a/@href" /> 
						<Result>			
							<Title>
								<xsl:value-of select="$Item" />
							</Title> 
							<Link>
							</Link>
						</Result>
				</xsl:for-each>
			</Results>
			 
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
	
</xsl:stylesheet>