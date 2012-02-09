package productionLine;

public class Producer implements Runnable {

	private ProductionLine queue;

	public Producer(ProductionLine queue) {
		this.queue = queue;
	}

	public void run() {
		int count = 0;
		while (count < 20) {
		
			synchronized(queue){
			if(queue.size() < 10) {
				Product p = new Product();
				System.out.println("Produced: " + p);
				queue.append(p);
				count++;		
			}
			}
			try {
				Thread.sleep(100);
			} catch (Exception ex) {
			}
		}
	}

}