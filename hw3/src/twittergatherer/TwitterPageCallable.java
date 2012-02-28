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
			connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setDoOutput(true);
	        connection.connect();

	        status = connection.getResponseCode();
			urlReader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
