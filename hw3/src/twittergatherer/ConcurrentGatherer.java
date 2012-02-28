package twittergatherer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ExecutionException;

public class ConcurrentGatherer extends TwitterGatherer {

	@Override
	public void getTweets(ArrayList<String> userNames) throws IOException {

		final int numCores = Runtime.getRuntime().availableProcessors();
		final double blockingCoef = .99;
		final int numThreads = (int) (numCores / (1-blockingCoef));
		
		System.out.println("num threads: " + numThreads);
		
		
		final ExecutorService threadPool = Executors.newFixedThreadPool(userNames.size());
		ArrayList<Callable<Integer>> userTasks = new ArrayList<Callable<Integer>>();
		final int maxThreads = numThreads / userNames.size() <= 0 ? 1 : numThreads / userNames.size();
		for(final String user : userNames){
			userTasks.add(new Callable<Integer>() {
				public Integer call() {
					Integer status = 0;
					try{
						getTweetsForUser(user, maxThreads);
					}catch(IOException e){
						status = -1;
					}
					return status;
				}
			});
		}
		
		try {
			// invoke all threads
			final List<Future<Integer>> statuses = threadPool.invokeAll(userTasks, 1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			threadPool.shutdown();	
		}
			
	}

	public void getTweetsForUser(String userName, int maxThreads) throws IOException{
		BufferedReader urlReader;
		StringBuffer tweet = new StringBuffer();
		
		final ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);
		ArrayList<Callable<String>> pages = new ArrayList<Callable<String>>();
		BufferedWriter writer = new BufferedWriter(new FileWriter(userName));
		tweet.setLength(0); // clear the current buffer 
			
			// loop over all 16 pages for each user
			for(int i =0; i< 16; i++){
				System.out.println("Working on user " + userName + " page: " + i);
				final URL url = new URL(this.generateURL(userName, i+1));
				try {
					pages.add(new Callable<String>(){
						public String call(){
							try {
								return getPageOfTweets(url);
							} catch (ExecutionException e) {
								return "ERROR";
							}
						}
						
					});
					
					List<Future<String>> allPages = threadPool.invokeAll(pages, 1000, TimeUnit.SECONDS);
					
					// concatenate all the strings
					for(Future<String> s : allPages){
						tweet.append(s.get());
					}
				} catch (ExecutionException e) {
					// in the event that we are being rate limited bail out.
					writer.write(tweet.toString());
					writer.close();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// dump the entire buffer to the file 
			writer.write(tweet.toString());
			writer.close();
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
				throw new ExecutionException("Being rate limited bailing out", e);
			}
		}

		return retval;
	}
	
}
