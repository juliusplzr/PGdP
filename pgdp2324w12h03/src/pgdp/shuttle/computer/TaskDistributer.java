package pgdp.shuttle.computer;

import java.util.List;

import pgdp.shuttle.tasks.ShuttleTask;
import pgdp.shuttle.tasks.TaskGenerator;

/**
 * This class generates tasks using the provided TaskGenerator until it reaches tasksToGenerate or it's shut down,
 * and sends them to each ShuttleProcessors.
 */
public class TaskDistributer extends Thread implements ShuttleComputerComponent {
	private final long tasksToGenerate;
	private final List<ShuttleProcessor> processors;
	private final TaskGenerator generator;

	private long currentTaskCount;

	public TaskDistributer(long tasksToGenerate, List<ShuttleProcessor> processors, TaskGenerator generator) {
		this.tasksToGenerate = tasksToGenerate;
		this.processors = processors;
		this.generator = generator;
		currentTaskCount = 0;
	}

	@Override
	public void run() {
		System.out.println("TaskDistributer starting to generate tasks.");
		try {
			while (currentTaskCount < tasksToGenerate && !shutDown) {
				ShuttleTask<?, ?> s = generator.generateTask();
				for (ShuttleProcessor p : processors) {
					p.addTask(s);
					synchronized (p) {
						p.notify();
					}
				}
				currentTaskCount++;
			}
		} catch (InterruptedException e) {
			System.out.println("TaskDistributer was interrupted after " + currentTaskCount + " tasks!");
			return;
		}
		System.out.println("TaskDistributer finished generating " + currentTaskCount + "/" + tasksToGenerate
				+ " tasks. Shutting down.");
	}

	private boolean shutDown;

	@Override
	public void shutDown() {
		shutDown = true;
		synchronized (this) {
			this.notify();
		}
	}
}
