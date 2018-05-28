package shareTwitterList;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class listshare {
	
	public static void main(String args[]) throws TwitterException, IOException {
		

		
		Twitterer twitterer1 = new Twitterer();	
		User user1 = twitterer1.getTwitter().verifyCredentials();
		
		
		
		Twitterer twitterer2 = new Twitterer();
		User user2 = twitterer2.getTwitter().verifyCredentials();
		
		copyList cp = new copyList(twitterer1,twitterer2);
		copyList cp2 = new copyList(twitterer2,twitterer1);
		cp.copy();
		cp2.copy();

		
	}
}
