
/**
 * Lab 05 Code Exercise 2 Task 1
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 27, 2020
 * 
 *        Sources: Code base from D2L Description: creates 5 threads, waits till they all finish. Finally sums values found in each thread
 */

import java.util.ArrayList;

public class RandomNumberExerciseTask1 {

	public static void main(String args[]) throws InterruptedException {
		int sum = 0;

		ArrayList<RandomNumThread> threadArr = new ArrayList<>();

		RandomNumThread r1 = new RandomNumThread();
		RandomNumThread r2 = new RandomNumThread();
		RandomNumThread r3 = new RandomNumThread();
		RandomNumThread r4 = new RandomNumThread();
		RandomNumThread r5 = new RandomNumThread();

		Thread t1 = new Thread(r1); // creating runnable threads
		Thread t2 = new Thread(r2);
		Thread t3 = new Thread(r3);
		Thread t4 = new Thread(r4);
		Thread t5 = new Thread(r5);

		threadArr.add(r1);
		threadArr.add(r2);
		threadArr.add(r3);
		threadArr.add(r4);
		threadArr.add(r5);

		System.out.println("Task 1 Threads starting");

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t5.join(); // waits for last thread to finish before executing code.

		System.gc(); // remove all above threads from memory

		for (RandomNumThread r : threadArr) {
			// System.out.println(r.getNum());
			sum += r.getNum();
		}
		System.out.println("Sum of all threads random number: " + sum);

	}
}
