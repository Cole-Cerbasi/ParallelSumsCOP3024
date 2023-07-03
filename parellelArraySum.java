package cop3024_Concurrency;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;  

class runSum extends Thread {
	int[] chunk;
	parallelSum main;
	int total = 0;
	public runSum(int[] arraySlice) {
		//chunk of main array
		this.chunk = arraySlice;
	}
	public void run() {
		for(int i = 0; i < this.chunk.length; i++) {
			this.total += this.chunk[i];
		}
	}
}

class parallelSum{
	int arrayChunk;
	int total = 0;
	int[] megaArray;
	int threadCount;
	parallelSum(int[] megaArray, int threadCount){
		//splitting the big array into little bits for each thread
		this.arrayChunk = (int)(megaArray.length / threadCount);
		this.megaArray = megaArray;
		this.threadCount = threadCount;
	}
	
	void compute(){
		runSum[] threads = new runSum[this.threadCount];
		
		for(int i = 0; i < this.threadCount; i++) {
			//using copyOfRange to grab a chunk of the big array and then give it to a thread to work on...
			threads[i] = new runSum(Arrays.copyOfRange(this.megaArray, (int)(i*this.arrayChunk), (int)((i*this.arrayChunk) + this.arrayChunk)));
			threads[i].start();
		}
		
		for(int i = 0; i < this.threadCount; i++) {
			try {
				//adding threads back into main thread
				threads[i].join();
				this.total += threads[i].total;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}

public class parellelArraySum {
	
	static int big = 200_000_000;
	static int[] Array = new int[big];
	
	public static void main(String[] args) {
		
		for (int i = 0; i < big; i++) {
			Array[i] = (int)((Math.random() * 10) + 1);
	    }
		
		//testing with 5 threads
		parallelSum arraySum5 = new parallelSum(Array, 5);
		
		long startTime = System.nanoTime();
		
		arraySum5.compute();
		
		long endTime = System.nanoTime();
		
		System.out.println("5 Thread: "+arraySum5.total+"  in "+(endTime - startTime)+"ns, "+(TimeUnit.NANOSECONDS.toMillis(endTime-startTime))+" ms...");
		
		//testing with 1 thread
		parallelSum arraySum1 = new parallelSum(Array, 1);
				
		startTime = System.nanoTime();
				
		arraySum1.compute();
				
		endTime = System.nanoTime();
				
		System.out.println("1 Thread: "+arraySum1.total+"  in "+(endTime - startTime)+"ns, "+(TimeUnit.NANOSECONDS.toMillis(endTime-startTime))+" ms...");
		
		//for some reason, running the 1 thread version and the 5 thread version back to back causes the 1 thread version to be faster than the 5 thread version. but when running them
		//seperately the 5 thread version is much faster! 
		
	}
}
