package pgdp.threads;

import java.util.concurrent.Semaphore;

public class Buffer {
	private Exam[] data;
	private Semaphore free;
	private Semaphore occupied;
	int first = 0, last = 0;

	public Buffer(int maxSize) {
		if (maxSize < 0)
			throw new IllegalArgumentException(
					"Buffer must have positive size!");
		data = new Exam[maxSize];
		free = new Semaphore(maxSize);
		occupied = new Semaphore(0);
	}

	public Exam consume() throws InterruptedException {
		Exam result;
		occupied.acquire();
		synchronized (this) {
			result = data[first];
			first = (first + 1) % data.length;
		}
		free.release();
		return result;
	}

	public void produce(Exam e) throws InterruptedException {
		free.acquire();
		synchronized (this) {
			data[last] = e;
			last = (last + 1) % data.length;
		}
		occupied.release();
	}
}