package cop3024_Concurrency;

import static org.junit.Assert.*;

import org.junit.Test;

public class compute_test {

	@Test
	public void test() {
		
		
		int[] Array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
			
		parallelSum arraySum = new parallelSum(Array, 5);
		arraySum.compute();
			
		System.out.println(arraySum.total);
		assertEquals(arraySum.total, 110);
		
	}

}
