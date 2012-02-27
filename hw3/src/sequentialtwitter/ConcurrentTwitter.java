package sequentialtwitter;

import java.io.IOException;
import java.util.ArrayList;

public class ConcurrentTwitter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("concurrent twitter running");
		
		ConcurrentGatherer twitter = new ConcurrentGatherer();
		ArrayList<String> userNames;
		try {
			userNames = twitter.getUserNames("users.txt");
			twitter.getTweets(userNames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
