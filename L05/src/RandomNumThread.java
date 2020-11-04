
/**
 * Lab 05 Code Exercise 2 Class
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 27, 2020
 * 
 *        Sources: Code base from D2L Description: common thread for each task to use. 
 *        Contains a shared results list. Generates a random number between 0 and 100
 */

import java.util.List;
import java.util.Random;

public class RandomNumThread implements Runnable {

	private int num;
	private List<Integer> results; // for task 2/3

	RandomNumThread() {
		// base constructor
	}

	RandomNumThread(List<Integer> results) {
		// constructor with results storage object passed
		this.results = results;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int newNum) {
		this.num = newNum;
	}

	@Override
	public void run() {
		Random randy = new Random();
		setNum(randy.nextInt(101)); // gets int between 0-100
		if (results != null) {
			results.add(randy.nextInt(101));
		}
	}

}
