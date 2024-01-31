package pgdp.shuttle.tasks;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the implementation of a TaskGenerator generating 
 * ShuttleTasks producing wrong result with specified probability
 */
public class ErrorProneTaskGenerator implements TaskGenerator {
	private AtomicInteger count;
	private final Random r = new Random();

	private final int errorProb;

	public ErrorProneTaskGenerator(int errorProb) {
		this.errorProb = errorProb;
		count = new AtomicInteger();
	}

	@Override
	public ShuttleTask<?, ?> generateTask() {
		final int j = r.nextInt();
		return new ShuttleTask<Random, String>(new Random(), (i) -> {
			count.incrementAndGet();
			if (i.nextInt(100) + 1 <= errorProb) {
				return "Error";
			} else {
				return "Number " + j;
			}
		});
	}

	public int getCount() {
		return count.get();
	}

}
