package pgdp.shuttle.computer;

import java.util.LinkedList;
import java.util.List;

import pgdp.shuttle.tasks.ErrorProneTaskGenerator;
import pgdp.shuttle.tasks.ErrorlessTaskGenerator;
import pgdp.shuttle.tasks.TaskGenerator;

/**
 * This class assembles all ShuttleComputerComponents together
 */
public class ShuttleComputer extends Thread {

	private final long tasksToGenerate;
	private final TaskGenerator generator;
	private final long sleepTime;

	public ShuttleComputer(long tasksToGenerate, TaskGenerator generator, long sleepTime) {
		this.tasksToGenerate = tasksToGenerate;
		this.generator = generator;
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		List<ShuttleProcessor> processors = new LinkedList<>();
		ShuttleOutput output = new ShuttleOutput();
		TaskChecker checker = new TaskChecker(processors, output);
		for (int i = 0; i < 4; i++) {
			processors.add(new ShuttleProcessor(checker));
		}
		TaskDistributer distributer = new TaskDistributer(tasksToGenerate, processors, generator);

		List<Thread> threads = new LinkedList<>();
		threads.add(distributer);
		threads.addAll(processors);
		threads.add(checker);
		threads.add(output);

		System.out.println("ShuttleComputer booting up.");
		for (Thread t : threads) {
			t.start();
		}

		try {
			sleep(sleepTime);
			distributer.shutDown();
			distributer.join();
		} catch (InterruptedException e) {
			System.out.println("ShuttleComputer crashed (interrupted while waiting for TaskDistributer)!");
			return;
		}
		threads.remove(0);

		for (Thread t : threads) {
			((ShuttleComputerComponent) t).shutDown();
		}
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println("ShuttleComputer crashed (interrupted while waiting for ShuttleComputerComponent)!");
				return;
			}
		}
		System.out.println("ShuttleComputer shutting down.");
	}

	public static void main(String[] args) {
		boolean errorless = false;
		int tasks = 1000000;
		long sleepTime = 100;

		if (errorless) {
			// Random tasks with deterministic results
			ErrorlessTaskGenerator g = new ErrorlessTaskGenerator(42);
			ShuttleComputer sG = new ShuttleComputer(tasks, g, sleepTime);
			sG.start();
			try {
				sG.join();
			} catch (InterruptedException i) {
				i.printStackTrace();
			}
		} else {
			// Equal tasks with random result
			ErrorProneTaskGenerator e = new ErrorProneTaskGenerator(20);
			ShuttleComputer sE = new ShuttleComputer(tasks, e, sleepTime);
			sE.start();
			try {
				sE.join();
			} catch (InterruptedException i) {
				i.printStackTrace();
			}
			// task count output for ErrorProneTaskGenerator (may give a hint, if everything works as intended)
			System.out.println(e.getCount());
		}
	}
}
