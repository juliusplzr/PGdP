package pgdp.ds;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stack {
	private Stack next;
	private final int[] mem;
	private int top;
	private final ReentrantReadWriteLock lock;

	public Stack(int capacity, ReentrantReadWriteLock lock) {
		next = null;
		mem = new int[capacity];
		top = -1;
		this.lock = lock;
	}

	public boolean isEmpty() {
		lock.readLock().lock();
		try {
			return top == -1;
		} finally {
			lock.readLock().unlock();
		}
	}

	public boolean isFull() {
		lock.readLock().lock();
		try {
			return top == mem.length - 1;
		} finally {
			lock.readLock().unlock();
		}
	}

	public void push(int val) {
		lock.writeLock().lock();
		try {
			if (isFull()) {
				if (next == null) {
					next = new Stack(mem.length * 2, lock);
				}
				next.push(val);
			} else {
				mem[++top] = val;
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

	public int pop() {
		lock.writeLock().lock();
		try {
			if (next != null) {
				final int pop = next.pop();
				if (next.isEmpty()) {
					next = null;
				}
				return pop;
			}
			return mem[top--];
		} finally {
			lock.writeLock().unlock();
		}
	}

	public int top() {
		lock.readLock().lock();
		try {
			if (next != null) {
				return next.top();
			}
			return mem[top];
		} finally {
			lock.readLock().unlock();
		}
	}

	public int size() {
		lock.readLock().lock();
		try {
			int size = top + 1;
			if (next != null) {
				size += next.size();
			}
			return size;
		} finally {
			lock.readLock().unlock();
		}
	}

	public int search(int element) {
		lock.readLock().lock();
		try {
			int pos = -1;
			if (next != null) {
				pos = next.search(element);
			}
			if (pos != -1) {
				return pos;
			}
			for (int i = top; i >= 0; i--) {
				if (mem[i] == element) {
					return top - i + (next != null ? next.size() : 0) + 1;
				}
			}
			return -1;
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		lock.readLock().lock();
		sb.append("{\ncapacity = ").append(mem.length).append(",\n");
		sb.append("mem = [")
				.append(IntStream.range(0, top + 1).mapToObj(x -> "" + mem[x]).collect(Collectors.joining(", ")))
				.append("],\n");
		sb.append("next = ").append(next == null ? "null" : "\n" + next.toString()).append("\n}\n");
		lock.readLock().unlock();
		return sb.toString();
	}
}
