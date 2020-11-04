
/**
 * Lab 05 Code Exercise 2 Task 2
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 27, 2020
 * 
 *        Sources: Code base from D2L Description: creates 5 threads, waits till they all finish. Finally sums values found in results arraylist
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomNumberExerciseTask2{

	public static void main(String args[]) throws InterruptedException {
		
		// task 2: using a collection to get results. Uses new random numbers
		List<Integer> results = Collections.synchronizedList(new ArrayList<>());
		
		Thread nt1 = new Thread(new RandomNumThread(results));
		Thread nt2 = new Thread(new RandomNumThread(results));
		Thread nt3 = new Thread(new RandomNumThread(results));
		Thread nt4 = new Thread(new RandomNumThread(results));
		Thread nt5 = new Thread(new RandomNumThread(results));
		
		System.out.println("Task 2 Threads starting");
		
		nt1.start();
		nt2.start();
		nt3.start();
		nt4.start();
		nt5.start();
		nt5.join(); // waits for last thread to finish before executing code.
		
		System.out.println("Sum of all threads random number: " + results.stream().mapToLong(Integer::longValue).sum());

	}
}
