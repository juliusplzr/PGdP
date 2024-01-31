package pgdp.ds;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MultiStack {

	private final Stack stacks;
	private final ReentrantReadWriteLock lock;

	public MultiStack() {
		lock = new ReentrantReadWriteLock();
		stacks = new Stack(1, lock);
	}

	public void push(int val) {
		stacks.push(val);
	}

	public int pop() {
		if (stacks.isEmpty()) {
			return Integer.MIN_VALUE;
		}
		return stacks.pop();
	}

	public int top() {
		if (stacks.isEmpty()) {
			return Integer.MIN_VALUE;
		}
		return stacks.top();
	}

	public int size() {
		if (stacks.isEmpty()) {
			return 0;
		}
		return stacks.size();
	}

	public int search(int element) {
		if (stacks.isEmpty()) {
			return -1;
		}
		return stacks.search(element);
	}

	@Override
	public String toString() {
		return stacks.toString();
	}

	public static void main(String[] args) {
		MultiStack m = new MultiStack();
		for (int i = 0; i < 20; i++) {
			m.push(i);
			System.out.println(m.toString() + " - " + m.size());
		}

		for (int i = 20; i >= 0; i--) {
			System.out.println(i + ": " + m.search(i));
		}

	}
}
