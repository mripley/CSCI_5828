package twittergatherer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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
		
		// loop through all the users and add a task for each
		for(final String user : userNames){
			userTasks.add(new Callable<Integer>() {
				public Integer call() {
					Integer status = 0;
					try{
						getTweetsForUser(user, maxThreads);
					}catch(IOException e){
						status = -1;
					} catch (ExecutionException e) {
						System.out.println("Caught execution exception in call method for users");
						// we are being rate limited shut down as quickly as possible 
						threadPool.shutdownNow();
					}
					return status;
				}
			});
		}
		
		try {
			// invoke all threads
			final List<Future<Integer>> statuses = threadPool.invokeAll(userTasks, 1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Caught Interrupted Exception");
		}
		finally{
			threadPool.shutdown();	
		}
			
	}

	public void getTweetsForUser(String userName, int maxThreads) throws IOException, ExecutionException{
		StringBuffer tweet = new StringBuffer();
		
		final ExecutorService threadPool = Executors.newFixedThreadPool(maxThreads);
		ArrayList<Callable<String>> pages = new ArrayList<Callable<String>>();
		BufferedWriter writer = new BufferedWriter(new FileWriter(userName));
		tweet.setLength(0); // clear the current buffer 
			
		// loop over all 16 pages for each user and create a task for each page. 
		for(int i =0; i< 16; i++){
			System.out.println("Working on user " + userName + " page: " + i);
			final URL url = new URL(this.generateURL(userName, i+1));
			pages.add(new TwitterPageCallable(url));
		}
		
		List<Future<String>> allPages;
		try {
			allPages = threadPool.invokeAll(pages, 1000, TimeUnit.SECONDS);
			
			// concatenate all the strings
			for(Future<String> s : allPages){
				tweet.append(s.get());
			}
		} catch (InterruptedException e) {
			System.out.println("Caught interruptedException");
		} catch (ExecutionException e) {
			System.out.println("Caught exceution exception due to rate limiting");
			throw new ExecutionException("Being rate limited bailing out", e);
		}
		finally{	
			// dump the entire buffer to the file 
			writer.write(tweet.toString());
			writer.close();
			threadPool.shutdownNow();
		}
	}
}
