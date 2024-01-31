package pgdp.shuttle.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class ShuttleTask<T, R> {
	private final T input;
	private final Function<T, R> function;
	private final List<R> computedResults;
	private R result;
	private boolean completed;

	public ShuttleTask(T input, Function<T, R> function) {
		this.input = Objects.requireNonNull(input);
		this.function = Objects.requireNonNull(function);
		computedResults = new ArrayList<>(4);
		completed = false;
	}

	/**
	 * Evaluates the function and adds the result to computedResults
	 */
	public void evaluate() {
		R res = function.apply(input);
		addResult(res);
	}

	private synchronized void addResult(R newResult) {
		computedResults.add(newResult);
	}

	/**
	 * Checks, if a result was computed at least 4 times
	 * @return true if 4 or more results have been computed, else false 
	 */
	public synchronized boolean readyToCheck() {
		return computedResults.size() >= 4;
	}

	/**
	 * Checks, if the computation was successful or not, 
	 * and sets the task to completed and result to the computed value (if successful).
	 * A task was computed successful, if at least 3 results are the same.
	 * @return true if computation was successful, else false  
	 */
	public boolean computationSuccessfull() {
		if (!readyToCheck()) {
			return false;
		}
		for (int i = 0; i < 2; i++) {
			final int k = i;
			if (computedResults.stream().filter(j -> j.equals(computedResults.get(k))).count() >= 3) {
				result = computedResults.get(i);
				completed = true;
				return true;
			}
		}
		return false;
	}

	/*
	 * Clears computedResults
	 */
	public void reset() {
		computedResults.clear();
	}

	public boolean wasCompleted() {
		return completed;
	}

	/**
	 * Returns the result of this task, if it was completed.
	 * Otherwise throws a RuntimeException
	 * @return the result
	 */
	public R getResult() {
		if (!completed) {
			throw new RuntimeException("Trying to access result that has not been computed or confirmed!");
		}
		return result;
	}
}
