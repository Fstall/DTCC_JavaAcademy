package com.rstech.journal.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;




import com.rstech.imagelib.ImageProcessing;
import com.rstech.journal.search.TitleExtractor;
import com.rstech.search.RSTechJournalSearchClient; 
import com.rstech.utility.URLEncoder;
import com.rstech.utility.RSWord;
import com.rstech.wordwatch.business.domain.RSUserManager;
import com.rstech.wordwatch.business.domain.WDUserReportManager;
import com.rstech.wordwatch.business.domain.WordReportEntryManager;
import com.rstech.wordwatch.business.domain.WordReportManager;
import com.rstech.wordwatch.dao.RSUser;
import com.rstech.wordwatch.dao.WDUserReport;
import com.rstech.wordwatch.dao.WordReport;
import com.rstech.wordwatch.dao.WordReportEntry;

public class RSTechJournalServlet extends HttpServlet { 
	private static final Class thisClass = RSTechJournalServlet.class;
	private static final Logger logger = Logger.getLogger(thisClass);


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		Cookie[] requestCookies = request.getCookies();
        
        if(requestCookies != null){
        for(Cookie c : requestCookies){
            System.out.println("Name="+c.getName()+", Value="+c.getValue()+", Comment="+c.getComment()
                    +", Domain="+c.getDomain()+", MaxAge="+c.getMaxAge()+", Path="+c.getPath()
                    +", Version="+c.getVersion());
            
        }
        }
		this.doPost(request, response);
		
		int count = 0;
		count++;
        Cookie counterCookie = new Cookie("Counter", String.valueOf(count));
        //add some description to be viewed in browser cookie viewer
        counterCookie.setComment("SetCookie Counter");
        //setting max age to be 1 day
        counterCookie.setMaxAge(24*60*60);
        //set path to make it accessible to only this servlet
        counterCookie.setPath("/ServletCookie/cookie/SetCookie");
 
        //adding cookie to the response
        response.addCookie(counterCookie);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = "doPost";
		logger.debug("entering " + methodName);

		try {
			String pageTitle = "TBD";
			String userId = request.getParameter("user"); 
			
			if (userId == null || userId.trim().length() == 0) {
				throw new RuntimeException("RSTechJournalServlet_ERROR|Unknown user error!|RSTechJournalServlet_ERROR");
			}
				
			RSUserManager userMgr = new RSUserManager();
			RSUser aUser = userMgr.getFirstUserByLogin(userId);
			if (aUser == null) {
				throw new RuntimeException("RSTechJournalServlet_ERROR|Cannot find user!|RSTechJournalServlet_ERROR");
			}
			
			//*** Check for password ....
			
			int count = RSUserManager.registerIfCanSearch(aUser, RSUserManager.LONGMAN_SEARCH_TYPE);
			if (RSUserManager.UserSearchCountLimit <= count) {
				throw new RuntimeException("RSTechJournalServlet_ERROR|Sorry, you have exceeded the maximum query allowance for this application prototype!|RSTechJournalServlet_ERROR");
			}

			String password = request.getParameter("pd"); 
			
			String url = request.getParameter("url");
			if (url != null && url.length() > 0) {
				try {
					new URL(url); 
					pageTitle = TitleExtractor.getPageTitle(url);
			    } catch (MalformedURLException malformedURLException) {
			        malformedURLException.printStackTrace();
			    } catch (Exception ex) {
			    	url = "unreacherable url: " + url;
			    } 
			}
			
			String query = request.getParameter("query");
			ArrayList<String> stringList = new ArrayList<String>();
			
			String contextSentence = request.getParameter("sample");
			ArrayList<String> samples = new ArrayList<String>();

			StringTokenizer token = new StringTokenizer(query, " ,+");

			while (token.hasMoreElements()) {
				String argument = token.nextToken();
				stringList.add(argument.trim());
				samples.add(contextSentence);
			}

			ArrayList<RSWord> wordList = convertObject(stringList, samples);
			RSTechJournalSearchClient mySearch = new RSTechJournalSearchClient();
			mySearch.populateDefinitions(wordList); 
			addWordToUserTest(wordList, url, pageTitle, contextSentence, userId);
			
			/** TODO
			 * To complete the renderering engine work.
			 */
			/* RenderPDFEngine doc = new RenderPDFEngine();

				RenderPDFTable.anotherRender(doc, 5, 4, wordList);
			 */
			/*
				SearchSite site = getSearchSite(request.getRequestURI());

				RsTfor (int i=0; i<rstechResult.getResults().size(); i++){
			RSTechResult result = (RSTechResult) rstechResult.getResults().get(i);
			String title = result.getTitle();
			String url = RSTechImageSearch.processImageURL(title);
			if (url != null) {
				werd.setImageURL(url);
				break;
			}

			String link = result.getLink();
			url = RSTechImageSearch.processImageURL(title);
			if (url != null) {
				werd.setImageURL(url);
				break;
			}
		}  echRequest rstechRequest = new RsTechRequest();
				getQuery(request, rstechRequest);
				getResults(request, rstechRequest);
				getPage(request, rstechRequest);
				getIncludeHtml(request, rstechRequest);
				getResolveUrls(request, rstechRequest);
				rstechRequest.setSite(site);
				String result = RSTechTransformerFactory.getTransformer(site).doTransform(rstechRequest);

				response.getWriter().append(result);
				response.getWriter().close();
			 */

		} catch (Exception e){
			response.sendError(400, e.getMessage());  
		} finally {
			logger.debug("exiting " + methodName);
		}
	}

	public ArrayList<RSWord> convertObject(ArrayList<String> stringList, ArrayList<String> sampleSentenceList) {
		String methodName = "convertObject";
		logger.debug("entering " + methodName);
		
		ArrayList<RSWord> wordList = new ArrayList<RSWord>();

		for (int i = 0; i < stringList.size(); i++) {
			RSWord werd = new RSWord();
			String argument = stringList.get(i);
			werd.setWordName(argument);
			String sentence = sampleSentenceList.get(i);
			werd.setSentence(sentence);
			wordList.add(werd);
		}
		logger.debug("exiting " + methodName);

		return wordList;
	} 



	protected void fetchImage() {

	}

	/**
	 * For this version - add to tchou the words.
	 * @param list
	 */
	protected void addWordToUserTest(ArrayList<RSWord> words, String originURL, String aTitle, String contextSentence, String userId) {
		String methodName = "addWordToUserTest";
		logger.debug("entering " + methodName);
		
		RSUserManager userMgr = new RSUserManager();
		RSUser aUser = userMgr.getFirstUserByLogin(userId);
		WordReport aReport = null;

		List<WDUserReport> userReports = null;
		WDUserReport userReport = null; 

		if (aUser != null) {
			// This function will create a report for the user if one is not found.
			userReport = WDUserReportManager.retrieveReportByUserAndURL(aUser.getLOGIN(), originURL, aTitle); 
		}  
		if (userReport != null) { 
			aReport = WordReportManager.getReportByID(userReport.getWD_REPORT());
		}

		if (userReport != null) { 
			ArrayList<String> rawWords = new ArrayList<String>();
			for (RSWord each : words) { 
				rawWords.add(each.getWordName());
			}

			List<WordReportEntry> entryList = WordReportEntryManager.findWordReportEntryFromList(rawWords, aReport);
			
			if (entryList != null && entryList.size() > 0) {
				for (WordReportEntry each : entryList) {
					System.out.println("WordReportEntry: " + each);
					Calendar calendar = Calendar.getInstance();
					java.sql.Date currentTimestamp = new java.sql.Date(calendar.getTime().getTime());
//					each.setENTRY_PUBLISHED_DATE(currentTimestamp);
					WordReportEntryManager.updatePublishDate(each, currentTimestamp);
					WordReportEntryManager.createAuxRecord(each.getID(), contextSentence, WordReportEntryManager.SENTENCE_KEY_CODE);
				}
			} else {
				RSWord[] y = words.toArray(new RSWord[0]);
				List<RSWord> aList = Arrays.asList(y);
				List<WordReportEntry> returnList = WDUserReportManager.addWordToReport(aList, aReport); 
				for (WordReportEntry eachEntry : returnList) {
					try {
						// the getImageURL is supposed to be the entire URL string
						// don't encode it... the fetchImage function will get the 
						// image filename from the URL and save it to /opt/image/ folder.
						// String url = eachEntry.getImageURL();
						String url = eachEntry.getIMG_URL3();
						String id = eachEntry.getID().toString();
						if (null != url) {
							ImageProcessing.fetchImage(url, id);
						} else {
							logger.warn("The fetched image url is NULL - check Google Image fetch code!" + methodName);
						}
						// 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				
			}
		}
		logger.debug("exiting " + methodName);
	} 
}
