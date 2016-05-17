package com.rstech.wordwatch.web.display;
import javax.xml.bind.annotation.XmlAttribute; 
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

public class WordOfTheDayOneExample {
		String language;
		String exampleText;

		@XmlAttribute (name = "lang") 
		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		@XmlElement (name="example")
		public String getExampleText() {
			return exampleText;
		}

		public void setExampleText(String exampleText) {
			this.exampleText = exampleText;
		}  
	}

