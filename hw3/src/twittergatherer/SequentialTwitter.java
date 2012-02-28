package twittergatherer;

import java.io.IOException;
import java.util.ArrayList;

public class SequentialTwitter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Sequential Twitter Running");
		SequentialGatherer tweetGatherer = new SequentialGatherer();
		final long start = System.nanoTime();
		try {
			ArrayList<String> userNames = SequentialGatherer.getUserNames("users.txt");
			tweetGatherer.getTweets(userNames);
		} catch (IOException e) {
			System.out.println("Caught IO exception in main");
		} 
		final long end = System.nanoTime();
		
		System.out.println("Total time: " + (end-start)/1.0e9);
	}

}
