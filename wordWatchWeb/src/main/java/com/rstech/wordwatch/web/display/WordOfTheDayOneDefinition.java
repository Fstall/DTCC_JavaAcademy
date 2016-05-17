package com.rstech.wordwatch.web.display; 

import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlAttribute;
public class WordOfTheDayOneDefinition {
	String language;
	String definitionText;

	@XmlAttribute (name = "lang") 
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@XmlElement (name = "definition")
	public String getDefinitionText() {
		return definitionText;
	}

	public void setDefinitionText(String definitionText) {
		this.definitionText = definitionText;
	} 
}
