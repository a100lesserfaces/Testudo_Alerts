import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.Console;
import java.io.IOException;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Tweet {
	
	// Consumer Key
	private final static String CONSUMER_KEY = "1JbVFBL5XltNv6wituN002kiY";

	// Consumer Secret

	private final static String CONSUMER_SECRET = "8FhU4Wx6HplrwB9OclmGZEon1SKKIubOhu77WkDakxuDTJLzc4";

	// Access Token
	private static String ACCESS_TOKEN = "718632210799206400-FI7G0uh02nS4BraoBXb23D9M1M25xX7";

	// Access Token Secret
	private static String ACCESS_TOKEN_SECRET = "uSRIMZlvFE7dBHEK8VtkxSEkNmWNeaorEzpvhfnnSCGbE";
		
	
	public static void main(String[] args) {
	    RSSFeedParser parser = new RSSFeedParser("https://news.google.com/news/feeds?pz=1&cf=all&ned=en&hl=en&q=college+park+md&output=rss");
	    Feed feed = parser.readFeed();
	    for (FeedMessage message : feed.getMessages()) {
	      String tweet = constructTweet(message.toString());
	      if (tweet != null) {
		      try {
					authorizeTwitter(tweet);
				} catch (IOException | TwitterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	    }
	  }
	
	/*
	 * Ended up not using this because we figured out the access tokens
	 */
	public static String constructTweet(String url) {
		String tweet = "";
		String title = " ";
		try {
			title = getPageTitle(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tweet += title + " - " + url;
		if (title.equals("")) {
			return null;
		}
		return tweet;
	}
	
	
	/*
	 * Grabbed from a source online, but it's not great so might redo this later
	 */
	public static String getPageTitle(URL url) throws Exception {
	      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	 
	      Pattern pHead = Pattern.compile("(?i)</HEAD>");
	      Matcher mHead;
	      Pattern pTitle = Pattern.compile("(?i)</TITLE>");
	      Matcher mTitle;
	       
	      String inputLine;
	      boolean found=false;
	      boolean notFound=false;
	      String html = "";
	      String title=new String();
	      try{
	          while (!(((inputLine = in.readLine()) == null) || found || notFound)){
	              html=html+inputLine;
	              mHead=pHead.matcher(inputLine);
	              if(mHead.find()){
	                  notFound=true;
	                  }
	              else{
	                  mTitle=pTitle.matcher(inputLine);
	                  if(mTitle.find()){
	                      found=true;
	                      //System.out.println(inputLine);
	                  }
	              }                                       
	          }
	          in.close();
	      
	          html = html.replaceAll("\\s+", " ");
	          if(found){
	              Pattern p = Pattern.compile("(?i)<TITLE.*?>(.*?)</TITLE>");
	              Matcher m = p.matcher(html);            
	              while (m.find() == true) {
	                  title=m.group(1);
	                //System.out.println("Title "+title); 
	              }
	          }
	      }catch(Exception e){
	      }
	      return title;
	    }
	
	public static void sendTweet(String tweet) {
		char[] pwd = null;
		
		try {
			Console con = System.console();
			if (con != null) {
				pwd = con.readPassword("Password:");
			}
		} catch (Exception e) {
			//bleh
			System.out.println("We tried at least.");
			e.printStackTrace();
		}
		 //send tweet
	
	}
	

	private static void authorizeTwitter(String message) throws MalformedURLException, IOException, TwitterException {
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(CONSUMER_KEY);
		builder.setOAuthConsumerSecret(CONSUMER_SECRET);
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		Twitter twitter = factory.getInstance();
		//twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		twitter.setOAuthAccessToken(new AccessToken(ACCESS_TOKEN, ACCESS_TOKEN_SECRET));
		
		StatusUpdate statusUpdate = new StatusUpdate(message);
		//statusUpdate.setMedia("http://h1b-work-visa-usa.blogspot.com", new URL("http://lh6.ggpht.com/-NiYLR6SkOmc/Uen_M8CpB7I/AAAAAAAAEQ8/tO7fufmK0Zg/h-1b%252520transfer%252520jobs%25255B4%25255D.png?imgmax=800").openStream());
		//tweet or update status
		Status status = twitter.updateStatus(statusUpdate);
		System.out.println(status.toString());
	}
	
}

