package stddevApp;
import java.math.*;

public class StandardDeviation {
	/**
	 * @param inputArray - the array of input values 
	 */
	public static double compute(double[] inputArray){
		
	   double n = 0;
	   double mean = 0;
	   double M2 = 0;
	   double delta = 0; 
			   
	   for (double x : inputArray){
	        n = n + 1;
	        delta = x - mean;
	        mean = mean + delta/n;
	        if (n > 1){
	            M2 = M2 + delta*(x - mean);
	        }
	    }
	
	    return  Math.sqrt(M2/(n-1));
	}
	
}
