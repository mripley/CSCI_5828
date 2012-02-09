package productionLine;

public class Main {

	public static void main(String[] args) {
		ProductionLine queue = new ProductionLine();

		Thread monitor = new Thread(new Monitor(queue));

		monitor.setDaemon(true);
		monitor.start();

		Thread[] consumers = new Thread[5];

		Thread[] producers = new Thread[10];

		for (int i = 0; i < consumers.length; i++) {
			consumers[i] = new Thread(new Consumer(queue));
			consumers[i].setDaemon(true);
			consumers[i].start();
		}

		for (int i = 0; i < producers.length; i++) {
			producers[i] = new Thread(new Producer(queue));
			producers[i].start();
		}

	}

}
