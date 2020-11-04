
/**
 * Lab 05 Code Exercise 2 Task 3
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 27, 2020
 * 
 *        Sources: Code base from D2L Description: creates a fixed thread pool with 5 threads, executes each thread and sums results from arraylist shared resource
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RandomNumberExerciseTask3 {

	public static void main(String args[]) throws InterruptedException {

		int numThreads = 5;
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(numThreads);
		List<Integer> results = Collections.synchronizedList(new ArrayList<>());

		System.out.println("Task 3 Threads starting");
		
		for (int i = 0; i < numThreads; i++) {
			fixedThreadPool.execute(new RandomNumThread(results));
		}

		fixedThreadPool.shutdown();

		System.out.println("Sum of all threads random number: " + results.stream().mapToLong(Integer::longValue).sum());

		fixedThreadPool.awaitTermination(1, TimeUnit.SECONDS);

	}
}


