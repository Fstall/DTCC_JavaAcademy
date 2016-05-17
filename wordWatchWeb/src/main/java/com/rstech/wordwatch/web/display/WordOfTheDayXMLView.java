package com.rstech.wordwatch.web.display;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class WordOfTheDayXMLView extends AbstractView
{
    /** Creates a new instance of WordOfTheDayXMLView */
    public WordOfTheDayXMLView()
    {
    } 
    
    /**
     * This is the de-factor Spring MVC way of returning an XML from
     * the servlet.
     * 
     * See this post for the background search:
     * 
     * http://forum.spring.io/forum/spring-projects/container/14278-return-xml-not-modelandview
     * 
     * You must create the concrete class in this. Also, there must be a properties file under
     * WEB-INF. In this case, I call it xmlViews.properties.
     *
     * The wordOfTheDayXMLView must be the map entry put by the Controller.
     * 
     * wordOfTheDayXMLView.class=package com.rstech.wordwatch.web.display.WordOfTheDayXMLView;
     * 
     * In the WordWatch-servlet.xml, it defines the following to link everything.
     * 
     * <bean id="xmlViewResolver" class="org.springframework.web.servlet.view.XmlViewResolver">
  	 *	 <property name="basename" value="xmlViews"/>
	 * </bean>
     */

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Hashtable table = (Hashtable) model.get("model"); 
		WordOfTheDay word = (WordOfTheDay) table.get("wordOfTheDayXMLView");
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/xml");
		PrintWriter pr = response.getWriter();
	    //parse your data to XML
	    String xml = XmlUtil.generateXml(word);
	    pr.write(xml);
	}
 

}
