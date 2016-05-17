package com.rstech.wordwatch.web.display;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

public class WordOfTheDayEntry {

	Long id;

	String entryWord;

	private List<WordOfTheDayOneDefinition> definitions;

	private List<WordOfTheDayOneExample> examples;

	@XmlAttribute
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlAttribute 
	public String getEntryWord() {
		return entryWord;
	}

	public void setEntryWord(String entryWord) {
		this.entryWord = entryWord;
	}

	@XmlElement(name = "definitions")
	public List<WordOfTheDayOneDefinition> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<WordOfTheDayOneDefinition> definitions) {
		this.definitions = definitions;
	}

	@XmlElement(name = "examples")
	public List<WordOfTheDayOneExample> getExamples() {
		return examples;
	}

	public void setExamples(List<WordOfTheDayOneExample> examples) {
		this.examples = examples;
	}

}
