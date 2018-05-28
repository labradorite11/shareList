package shareTwitterList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class Twitterer {
	Twitter twitter;
	ResponseList<UserList> lists;
	ArrayList<ArrayList<User>> usersLists = new ArrayList<ArrayList<User>>();
	ArrayList<ArrayList<Long>> userNames = new ArrayList<ArrayList<Long>>();
	ArrayList<String> listNames = new ArrayList<String>();
	ArrayList<String> listSlugs = new ArrayList<String>();
	
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public Twitterer() throws IOException, TwitterException{
		Properties properties = new Properties();
		InputStream istream = new FileInputStream("src/main/resources/twitter4j.properties");
		properties.load(istream);
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(properties.getProperty("oauth.consumerKey"))
		  .setOAuthConsumerSecret(properties.getProperty("oauth.consumerSecret"))
		  .setOAuthAccessToken(null)
		  .setOAuthAccessTokenSecret(null);
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		
		while(accessToken == null) {
			System.out.println("Open the following URL and grant access to your secpmd acount:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out.println("Enter the PIN:");
			String pin = br.readLine();
			try {
			accessToken = twitter.getOAuthAccessToken(requestToken,pin);
			} catch (TwitterException te) {
				te.printStackTrace();
				
			}
		}
		getList();
		
	}
	
	public Twitter getTwitter() throws IllegalStateException, TwitterException {
		return this.twitter;
	}
	
	public void getList() throws TwitterException {
		lists = twitter.getUserLists(twitter.getScreenName());
		for(UserList list : lists) {
			listNames.add(list.getName());
			listSlugs.add(list.getSlug());
			long listID = list.getId();
			
			long cursor = -1;
			ArrayList<User> userArray = new ArrayList<User>();
			while(cursor != 0) {
			PagableResponseList<User> users = twitter.getUserListMembers(listID,cursor);
			for(User user : users) {
				userArray.add(user);
			}
			cursor = users.getNextCursor();
			}
			
			usersLists.add(userArray);
			
		}
		
		for(ArrayList<User> users : usersLists) {
			ArrayList<Long> userName = new ArrayList<Long>();
			for(User user : users) {
				userName.add(user.getId());
			}
			userNames.add(userName);
			
		}
			
	}
	
	public long getUserName(int x,int y) {
		return userNames.get(x).get(y);
	}
		
}
	
	

