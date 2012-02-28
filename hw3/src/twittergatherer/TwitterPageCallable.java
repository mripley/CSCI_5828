package twittergatherer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class TwitterPageCallable implements Callable<String> {

	private URL url;
	public TwitterPageCallable(URL url){
		this.url = url;
	}
	
	@Override
	public String call() throws ExecutionException {
		return getPageOfTweets(url);
	}
	
	public String getPageOfTweets(URL url) throws ExecutionException{
		BufferedReader urlReader;
		HttpURLConnection connection;
		String retval = "";
		int status = 0;
		try {
			// open the http connection to twitter
			connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(true);
	        connection.connect();
	        
	        // get the status of the connection so we can see if we are being rate limited
	        status = connection.getResponseCode();
			urlReader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
			// read the line. Since we are requesting JSON everything is on the same line
			retval = urlReader.readLine();
			urlReader.close();
		} catch (IOException e) {
			
			System.out.println("Caught IOException with http status: "+status);
			// so if something other than rate limiting happened
			if (status == 400){
				// we're being rate limited bail out now before we make things worse. 
				System.out.println("Twitter says: " + retval);
				throw new ExecutionException("Being rate limited bailing out", e);
			}
		}
		return retval;
	}

}
