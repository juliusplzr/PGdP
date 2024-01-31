package pgdp.shuttle.computer;

import java.util.concurrent.LinkedBlockingQueue;

import pgdp.shuttle.tasks.ShuttleTask;

/**
 * This class evaluates tasks and then passes them to the TaskChecker until it is shut down.
 * Tasks in priorityTaskQueue are preferred over Tasks in taskQueue.
 */
public class ShuttleProcessor extends Thread implements ShuttleComputerComponent {
	private final LinkedBlockingQueue<ShuttleTask<?, ?>> taskQueue;
	private final LinkedBlockingQueue<ShuttleTask<?, ?>> priorityTaskQueue;
	private final TaskChecker checker;

	public ShuttleProcessor(TaskChecker checker) {
		taskQueue = new LinkedBlockingQueue<>();
		priorityTaskQueue = new LinkedBlockingQueue<>();
		this.checker = checker;
	}

	public void addTask(ShuttleTask<?, ?> task) throws InterruptedException {
		taskQueue.put(task);
	}

	public void addPriorityTask(ShuttleTask<?, ?> task) throws InterruptedException {
		priorityTaskQueue.put(task);
	}

	@Override
	public void run() {
		try {
			while (!shutDown) {
				if (!priorityTaskQueue.isEmpty()) {
					evaluateTask(priorityTaskQueue.poll());
				} else if (!taskQueue.isEmpty()) {
					evaluateTask(taskQueue.poll());
				} else {
					synchronized (this) {
						wait();
					}
				}
			}
		} catch (InterruptedException e) {
			System.out.println("ShuttleProcessor was interrupted. Shutting down.");
			return;
		}
		System.out.println("ShuttleProcessor shutting down.");

	}

	private void evaluateTask(ShuttleTask<?, ?> task) throws InterruptedException {
		task.evaluate();
		checker.addTask(task);
		synchronized (checker) {
			checker.notify();
		}
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
