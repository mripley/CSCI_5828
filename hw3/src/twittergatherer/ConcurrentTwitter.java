package twittergatherer;

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
		
		final long start = System.nanoTime();
		try {
			userNames = ConcurrentGatherer.getUserNames("users.txt");
			twitter.getTweets(userNames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final long end = System.nanoTime();
		System.out.println("Total time: " + (end-start)/1.0e9);
	
	}

}
