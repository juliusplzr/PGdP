package pgdp.pools;

import java.util.HashMap;
import java.util.Objects;

public class TaskPool<T, R> {
	private final HashMap<Integer, Task<T, R>> taskPool = new HashMap<>();
	protected TaskPool() {
	}

	public Task<T, R> insert(Task<T, R> task) {
		if (taskPool.containsValue(task)) {
			return taskPool.get(task.hashCode());
		}

		taskPool.put(task.hashCode(), task);
		return task;
	}

	public Task<T, R> getByValue(T input, TaskFunction<T, R> function) {
		for (Task<T, R> task : taskPool.values()) {
			if (Objects.equals(task.getInput(), input) && Objects.equals(task.getTaskFunction(), function)) {
				return task;
			}
		}

		return null;
	}

	public static void main(String[] args) {
		TaskFunction<Integer, Integer> f = new TaskFunction<>(FunctionLib.SQUARE);
		TaskPool<Integer, Integer> tp = new TaskPool<>();

		System.out.println(tp.getByValue(1, f)); // null

		Task<Integer, Integer> t1 = new Task<>(1, f);
		Task<Integer, Integer> t2 = new Task<>(1, f);
		System.out.println(t1 == tp.insert(t1)); // true
		System.out.println(t1 == tp.insert(t2)); // true
		System.out.println(t1 == tp.getByValue(1, f)); // true
	}
}
