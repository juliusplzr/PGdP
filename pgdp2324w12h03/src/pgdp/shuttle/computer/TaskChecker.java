package pgdp.shuttle.computer;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import pgdp.shuttle.tasks.ShuttleTask;

/**
 * This class checks, whether a Task was evaluated successful or not and 
 * either passes them to the ShuttleOutput or returns them to the ShuttleProcessors
 * until it is shutDown.
 */
public class TaskChecker extends Thread implements ShuttleComputerComponent {
	private final LinkedBlockingQueue<ShuttleTask<?, ?>> taskQueue;
	private final List<ShuttleProcessor> processors;
	private final ShuttleOutput output;

	public TaskChecker(List<ShuttleProcessor> processors, ShuttleOutput output) {
		taskQueue = new LinkedBlockingQueue<>();
		this.processors = processors;
		this.output = output;
	}

	public void addTask(ShuttleTask<?, ?> task) throws InterruptedException {
		taskQueue.add(task);
	}

	@Override
	public void run() {
		try {
			while (!shutDown) {
				if (!taskQueue.isEmpty()) {
					ShuttleTask<?, ?> t = taskQueue.poll();
					if (!t.wasCompleted() && t.readyToCheck()) {
						if (t.computationSuccessfull()) {
							output.addTask(t);
							synchronized (output) {
								output.notify();
							}
						} else {
							t.reset();
							for (ShuttleProcessor s : processors) {
								s.addPriorityTask(t);
								synchronized (s) {
									s.notify();
								}
							}
						}
					}
				} else {
					synchronized (this) {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.println("TaskChecker was interrupted. Shutting down.");
			return;
		}
		System.out.println("TaskChecker shutting down.");
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
