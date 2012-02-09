package stddevTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import stddevApp.StandardDeviation;

public class StddevTest {
	

	StandardDeviation std;
	
	@Before
	public void setUp() throws Exception {
		std = new StandardDeviation();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPositiveArray() {
		double[] testArray1 = {2.846, 3.54, 5.874, 8.897, 5.12};
		double testArray1Std = std.compute(testArray1);
		System.out.println(testArray1Std);
		double actualStd = 2.36696f;
		assertEquals(testArray1Std, actualStd, 0.0001);
	}
	
	@Test
	public void testNegativeArray(){
		double[] testArray2 = {-2.154, -3.16, -9.16, -19.3654, -10.213};
		double testArray2Std = std.compute(testArray2);
		System.out.println(testArray2Std);
		double actualStd = 6.88717f;
		assertEquals(testArray2Std, actualStd, 0.0001);
	}
	
	@Test
	public void testZeroArray(){
		double[] testArray3 = {0,0,0,0,0,0,0,0,0};
		double testArray3Std = std.compute(testArray3);
		System.out.println(testArray3Std);
		double actualStd = 0.0f;
		assertEquals(testArray3Std, actualStd, 0.0001);
	}

}
