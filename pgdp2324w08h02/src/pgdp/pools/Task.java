package pgdp.pools;

import java.util.Objects;

public class Task<T, R> {
	private final T input;
	private R result;
	private final TaskFunction<T,R> taskFunction;

	protected Task(T input, TaskFunction<T, R> taskFunction) {
		this.input = input;
		this.taskFunction = taskFunction;
		result = null;
	}

	public T getInput() {
		return input;
	}

	public R getResult() {
		if (result == null) {
			apply();
		}

		return result;
	}

	public void apply() {
		if (result != null) {
			return;
		}

		result = taskFunction.apply(input);
	}

	public TaskFunction<T, R> getTaskFunction() {
		return taskFunction;
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, taskFunction);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (obj.getClass() != this.getClass()) {
			return false;
		}

		Task<?, ?> otherTask = (Task<?, ?>) obj;

		return Objects.equals(otherTask.input, this.input) && Objects.equals(otherTask.taskFunction, this.taskFunction);
	}

	public static void main(String[] args) {
		TaskFunction<Integer, Integer> f1 = new TaskFunction<>(FunctionLib.INC);
		TaskFunction<Integer, Integer> f2 = new TaskFunction<>(FunctionLib.INC);
		Task<Integer, Integer> t1 = new Task<>(1, f1);
		Task<Integer, Integer> t2 = new Task<>(1, f1);
		Task<Integer, Integer> t3 = new Task<>(1, f2);

		System.out.println(t1.equals(t2)); // true
		System.out.println(t1.equals(t3)); // false

		System.out.println(t1.getResult()); // 2
	}
}
