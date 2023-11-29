package pgdp.ds;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stack {
	private Stack next;
	private final int[] mem;
	private int top;

	public Stack(int capacity) {
		next = null;
		mem = new int[capacity];
		top = -1;
	}

	// TODO implement missing methods

	public int[] getMem() {
		return mem;
	}

	public Stack getNext() {
		return next;
	}


	public boolean isFull() {
		return mem.length - 1 == top;
	}

	public boolean isEmpty() {
		return top == -1;
	}

	public int getTop() {
		return this.top;
	}

	public int top() {
		if (this.isEmpty()) {
			return Integer.MIN_VALUE;
		}

		return mem[top];
	}

	public void setNext(Stack s) {
		next = s;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int pop() {
		if (next != null) {
			int pop = next.pop();

			if (next.isEmpty()) {
				next = null;
			}

			return pop;
		}

		int topTemp = top;
		top--;
		return mem[topTemp];
	}

	public void push(int element) {
		if (this.isFull() && next == null) {
			Stack next = new Stack(2 * this.getMem().length);
			this.next = next;
			next.push(element);
		} else {
			mem[top + 1] = element;
			top++;
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\ncapacity = ").append(mem.length).append(",\n");
		sb.append("mem = [")
				.append(IntStream.range(0, top + 1).mapToObj(x -> "" + mem[x]).collect(Collectors.joining(", ")))
				.append("],\n");
		sb.append("next = ").append(next == null ? "null" : "\n" + next.toString()).append("\n}\n");
		return sb.toString();
	}

	public static void main(String[] args) {
		Stack s = new Stack(3);
		s.push(2);
		s.push(3);
		s.push(4);
		s.push(5);
		s.pop();

		System.out.println("---- top ----");
		System.out.println(s.top());


		System.out.println("---- stack ----");
		System.out.println(s.toString());
	}
}
