package productionLine;

public class Consumer implements Runnable {

	private ProductionLine queue;

	public Consumer(ProductionLine queue) {
		this.queue = queue;
	}

	public void run() {
		while (true) {
			synchronized(queue){
			if (queue.size() > 0) {
				System.out.println("Consumed: " + queue.retrieve());
			}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception ex) {
			}
		}
	}

}
