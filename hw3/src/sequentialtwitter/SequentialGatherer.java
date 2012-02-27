package sequentialtwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
			    int status = 0;
				System.out.println("Working on user " + user + " page: " + i);
				url = new URL(this.generateURL(user, i+1));
				try{
					
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			        connection.setRequestMethod("GET");
			        connection.setDoOutput(true);
			        connection.connect();
			        status = connection.getResponseCode();
					urlReader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
					tweet.append(urlReader.readLine());
					urlReader.close();
				} catch(IOException e){
					
					// so if something other than rate limiting happened
					System.out.println("Caught IOException with http status: "+status);
					if(status != 400){
						// undo the page increment so we'll issue the request again
						//i--;
					}
					else{
						// we are probably being rate limited. Write what we have and exit
						writer.write(tweet.toString());
						writer.close();
						return;
					}
				} 
			}
			// dump the entire buffer to the file 
			writer.write(tweet.toString());
			writer.close();
		}
	}

}
