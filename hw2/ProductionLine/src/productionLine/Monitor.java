package productionLine;

public class Monitor implements Runnable {

	private ProductionLine queue;

	public Monitor(ProductionLine queue) {
		this.queue = queue;
	}

	public void run() {
		while (true) {
			synchronized(queue){
			int size = queue.size();
			if (size > 10) {
				System.out.println("Too many items in the queue: " + size + "!");
			} else if (size == 0) {
				System.out.println("Queue empty!");
			}
			}
			try {
				Thread.sleep(50);
			} catch (Exception ex) {
				
			}
		}
	}

}