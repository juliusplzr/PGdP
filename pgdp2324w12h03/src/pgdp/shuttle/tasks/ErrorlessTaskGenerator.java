package pgdp.shuttle.tasks;

import java.util.Random;
import java.util.function.Function;

/**
 * This class is the implementation of a TaskGenerator generating 
 * deterministic ShuttleTasks with different generic types
 */
public class ErrorlessTaskGenerator implements TaskGenerator {

	private static Random r;
	private final Function<Integer, String> f1 = (i) -> "Number: " + i;
	private final Function<Integer, Integer> f2 = (i) -> i * i;
	private final Function<Integer, String> f3 = f2.andThen(f1);

	public ErrorlessTaskGenerator() {
		r = new Random();
	}

	public ErrorlessTaskGenerator(long seed) {
		r = new Random(seed);
	}

	@Override
	public ShuttleTask<?, ?> generateTask() {
		int i = r.nextInt(1000) + 1;

		switch (r.nextInt(3)) {
		case 0:
			return new ShuttleTask<>(i, f1);
		case 1:
			return new ShuttleTask<>(i, f2);
		case 2:
			return new ShuttleTask<>(i, f3);
		default:
			// for SCA
			return new ShuttleTask<>(i, n -> n);
		}
	}
}
