package sequentialtwitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

public class ConcurrentGatherer extends TwitterGatherer {

	@Override
	public void getTweets(ArrayList<String> userNames) throws IOException {
		final int numCores = Runtime.getRuntime().availableProcessors();
		final double blockingCoef = .9;
		final int numThreads = (int) (numCores / (1-blockingCoef));
		
		System.out.println("num threads: " + numThreads);
		
		List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
		
		final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
		
		
	}

	
}
