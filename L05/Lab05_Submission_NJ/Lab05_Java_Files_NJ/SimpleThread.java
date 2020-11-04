
/**
 * Lab 05 Code Exercise 1 Task 1 & 2
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 27, 2020
 * 
 *        Sources: Code base from D2L Description: Main function creates
 *        multiple threads. Tasks 1 and 2 deal with creating a synchronized
 *        resource shared between threads that cannot be edited by both threads
 *        at once.
 */

public class SimpleThread extends Thread { // task 1/2 thread class

	Resource resource;

	public void run() {
		synchronized (resource) { // forcing threads to share the resource Task 1/2

			for (int i = 0; i < 10; i++) {
				try {
					System.out.println(resource.increment());

					Thread.sleep(2000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	SimpleThread(Resource resource) {
		this.resource = resource;
	}

	public static void main(String args[]) {
		Resource resource = new Resource();
		Thread t = new SimpleThread(resource);
		Thread s = new SimpleThread(resource);

		Thread tRunnable = new Thread(new SimpleThreadRunnable(resource));
		Thread sRunnable = new Thread(new SimpleThreadRunnable(resource));

		// t.start();
		// s.start();
		tRunnable.start();
		sRunnable.start();

	}

}

// Implementing task 3
class SimpleThreadRunnable implements Runnable {

	Resource resource;

	@Override
	public void run() {
		synchronized (resource) {
			for (int i = 0; i < 10; i++) {
				try {
					System.out.println(resource.increment());

					Thread.sleep(1);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}
	}

	SimpleThreadRunnable(Resource resource) {
		this.resource = resource;
	}

}
