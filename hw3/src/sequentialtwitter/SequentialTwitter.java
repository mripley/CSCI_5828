package sequentialtwitter;

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
		
		try {
			ArrayList<String> userNames = tweetGatherer.getUserNames("users.txt");
			tweetGatherer.getTweets(userNames);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
