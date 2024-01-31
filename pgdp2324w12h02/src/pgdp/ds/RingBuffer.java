package pgdp.ds;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RingBuffer {

	private final int[] mem;
	private int in;
	private int out;
	//	private int stored;
	private final AtomicInteger stored = new AtomicInteger();

	private final ReentrantLock takeLock = new ReentrantLock();
	private final Condition notEmpty = takeLock.newCondition();
	private final ReentrantLock putLock = new ReentrantLock();
	private final Condition notFull = putLock.newCondition();

	public RingBuffer(int capacity) {
		mem = new int[capacity];
		in = 0;
		out = 0;
		//		stored = 0;
	}

	public boolean isEmpty() {
		//		return stored == 0;
		return stored.get() == 0;
	}

	public boolean isFull() {
		//		return stored == mem.length;
		return stored.get() == mem.length;
	}

	public void put(int val) throws InterruptedException {
		int s;
		putLock.lockInterruptibly();
		try {
			while (isFull()) {
				notFull.await();
			}
			mem[in++] = val;
			in %= mem.length;
			s = stored.getAndIncrement();
			if (s + 1 < mem.length) {
				notFull.signal();
			}
		} finally {
			putLock.unlock();
		}
		if (s == 0) {
			takeLock.lock();
			try {
				notEmpty.signal();
			} finally {
				takeLock.unlock();
			}
		}
	}

	public int get() throws InterruptedException {
		int s, r;
		takeLock.lockInterruptibly();
		try {
			while (stored.get() == 0) {
				notEmpty.await();
			}
			r = mem[out++];
			out %= mem.length;
			s = stored.getAndDecrement();
			if (s > 1) {
				notEmpty.signal();
			}
		} finally {
			takeLock.unlock();
		}
		if (s == mem.length) {
			putLock.lock();
			try {
				notFull.signal();
			} finally {
				putLock.unlock();
			}
		}
		return r;
	}

	@Override
	public String toString() {
		putLock.lock();
		takeLock.lock();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("RingBuffer := { capacity = ").append(mem.length).append(", out = ").append(out).append(", in = ")
					.append(in).append(", stored = ").append(stored).append(", mem = ").append(Arrays.toString(mem))
					.append(", buffer = [");
			if (!isEmpty()) {
				if (in >= 0 || in < mem.length) {
					int i = out;
					do {
						sb.append(mem[i]).append(", ");
						i = (i + 1) % mem.length;
					} while (i != in);
					sb.setLength(sb.length() - 2);
				} else {
					sb.append("Error: Field 'in' is <").append(in)
							.append(">, which is out of bounds for an array of length ").append(mem.length);
				}
			}
			sb.append("] }");
			return sb.toString();
		} finally {
			takeLock.unlock();
			putLock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		RingBuffer bf = new RingBuffer(4);

		Thread p = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 25; i++) {
					try {
						bf.put(i);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		};

		Thread g = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 25; i++) {
					try {
						int v = bf.get();
						System.out.println(v);
					} catch (InterruptedException e) {
						e.printStackTrace();
						return;
					}

				}
			}
		};

		p.start();
		g.start();
		p.join();
		g.join();

		System.out.println(bf);
	}

}
