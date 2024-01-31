package pgdp.shuttle.computer;

import java.util.concurrent.LinkedBlockingQueue;

import pgdp.shuttle.tasks.ShuttleTask;

/**
 *	This class outputs Task results until it's shut down.
 */
public class ShuttleOutput extends Thread implements ShuttleComputerComponent {
	private final LinkedBlockingQueue<ShuttleTask<?, ?>> taskQueue;

	public ShuttleOutput() {
		taskQueue = new LinkedBlockingQueue<>();
	}

	public void addTask(ShuttleTask<?, ?> task) throws InterruptedException {
		taskQueue.put(task);
	}

	@Override
	public void run() {
		try {
			while (!shutDown) {
				if (!taskQueue.isEmpty()) {
					ShuttleTask<?, ?> t = taskQueue.poll();
					System.out.println("Result: " + t.getResult().toString());
				} else {
					synchronized (this) {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.println("ShuttleOutput was interrupted. Shutting down.");
			return;
		}
		System.out.println("ShuttleOutput shutting down.");
	}

	private boolean shutDown = false;

	@Override
	public void shutDown() {
		shutDown = true;
		synchronized (this) {
			this.notify();
		}
	}
}
