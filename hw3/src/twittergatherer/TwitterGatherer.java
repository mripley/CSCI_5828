package twittergatherer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class TwitterGatherer {

	// load the user names from the file
	protected static ArrayList<String> getUserNames(String fileName) throws IOException{
		ArrayList<String> userNames = new ArrayList<String>();
		// load the file
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String user = null;
		// every user is on a new line
		while((user = reader.readLine()) != null){
			user = user.replace("\n", ""); // replace the new line
			userNames.add(user);	// add the user to the list of users
		}
		return userNames;
	}
	
	// generate a URL for a given user name. 
	protected String generateURL(String userName, int page){
		return "http://twitter.com/statuses/user_timeline/" + userName + ".json?count=200&page=" + page;
	}

	public abstract void getTweets(ArrayList<String> userNames) throws IOException;
	
}
