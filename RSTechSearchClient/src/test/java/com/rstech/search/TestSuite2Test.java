package com.rstech.search;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test; 
import com.rstech.exception.RSTechException;
import com.rstech.utility.RSWord;

public class TestSuite2Test {

    private RSTechSearchClient reference;
    private String searchString = "versatile";
    private String searchResult = "";

    @Before
    public void setUp() throws Exception {
        reference = mock(RSTechSearchClient.class); 
        try {
			when(RSTechSearchClient.searchJson("LONGMAN", searchString, 10, 1, true, true)).thenReturn(searchResult);
		} catch (RSTechException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }

    @Test
    public void test() {
		RSTechJournalSearchClient testClient = new RSTechJournalSearchClient();

		RSWord searchWord = new RSWord();
		searchWord.setWordName(searchString);
		testClient.searchLongman(searchWord); 
		int j = 0;
		j++;
	 
         
    }
}
 
