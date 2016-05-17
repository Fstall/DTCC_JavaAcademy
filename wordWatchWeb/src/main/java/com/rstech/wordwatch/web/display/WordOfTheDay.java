package com.rstech.wordwatch.web.display;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement
public class WordOfTheDay {
	
	 
	 WordOfTheDayEntry entry;
	 
	
	/*
	 * 
	<WordOfTheDay>
	<entry id = “1234” >impasses	
<definitions>
	<definition lang = “EN”>
	</definition>
	<definition lang = “CN”>
	</definition>
	</definitions>
		<examples>
			<example lang = “EN”>
			</example>
			<example lang = “CN”>
			</example>
		</examples>
</ entry >
</WordOfTheDay>

	 */

	@XmlElement (name ="entry")
	public WordOfTheDayEntry getEntry() {
		return entry;
	}


	public void setEntry(WordOfTheDayEntry entry) {
		this.entry = entry;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		WordOfTheDay myWord = new WordOfTheDay();
		WordOfTheDayEntry myEntry = new WordOfTheDayEntry ();
		myEntry.setId(1234L);
		myEntry.setEntryWord("espionage");
		WordOfTheDayOneDefinition definitionOne = new WordOfTheDayOneDefinition();
		WordOfTheDayOneDefinition definitionTwo = new WordOfTheDayOneDefinition();
		definitionOne.setLanguage("EN");
		definitionOne.setDefinitionText("the act or practice of spying.");
		
		definitionTwo.setLanguage("EN");
		definitionTwo.setDefinitionText("the use of spies by a government to discover the military and political secrets of other nations.");

		WordOfTheDayOneExample exampleOne = new WordOfTheDayOneExample();
		WordOfTheDayOneExample exampleTwo = new WordOfTheDayOneExample();
		
		exampleOne.setLanguage("EN");
		exampleOne.setExampleText("After Moses died it was left to Joshua to take the land, and since he had a background in espionage - before pushing forward and taking Jericho, he sent in a team of spies. ");
		
		exampleTwo.setLanguage("EN");
		exampleTwo.setExampleText("The recent exposure of a secret Chinese military cyber warfare unit has not led to a decrease in cyber espionage against U.S. government.");

		ArrayList<WordOfTheDayOneDefinition> listOfDefinitions = new ArrayList<WordOfTheDayOneDefinition>();
		listOfDefinitions.add(definitionOne);
		listOfDefinitions.add(definitionTwo);
	
		ArrayList<WordOfTheDayOneExample> listOfExamples = new ArrayList<WordOfTheDayOneExample>();
		listOfExamples.add(exampleOne);
		listOfExamples.add(exampleTwo);
		
		myEntry.setDefinitions(listOfDefinitions);
		myEntry.setExamples(listOfExamples); 
		myWord.setEntry(myEntry);
		
		
		String output = XmlUtil.generateXml(myWord);
		
		System.out.println(output);
		
		WordOfTheDay theWord = (WordOfTheDay) XmlUtil.parseXml(output);
		
		System.out.println(theWord.getEntry().getEntryWord());
		System.out.println(theWord.getEntry().getId());
		
		
		

	}

}
