package stddevApp;

public class StddevApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("standard deviation running");
		StandardDeviation std = new StandardDeviation();
		double[] vals = {1.34f, 1.435f, 5.24f, 3.43f};
		double stddev = std.compute(vals);
		System.out.println("Standard Deviation: " + stddev);
	}
}
