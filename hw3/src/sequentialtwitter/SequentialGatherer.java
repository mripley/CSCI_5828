package sequentialtwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class SequentialGatherer extends TwitterGatherer {

	@Override
	public void getTweets(ArrayList<String> userNames) throws IOException  {
		URL url;
		BufferedReader urlReader;
		StringBuffer tweet = new StringBuffer();
		String line;
		for (String user : userNames){
			BufferedWriter writer = new BufferedWriter(new FileWriter(user));
			tweet.setLength(0); // clear the current buffer 
			
			// loop over all 16 pages for each user
			for(int i =0; i< 16; i++){
				url = new URL(this.generateCall(user, i+1));
				urlReader= new BufferedReader(new InputStreamReader(url.openStream()));
				
				// loop over all the lines and add them to the buffer
				while((line = urlReader.readLine())!= null){
					tweet.append(line);	
				}
			}
			
			// dump the entire buffer to the file 
			writer.write(tweet.toString());
			writer.close();
			
		}
	}

}
