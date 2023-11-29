package pgdp.ds;

public class MultiStack {

	private final Stack stacks;

	public MultiStack() {
		stacks = new Stack(1);
	}

	// TODO implement missing methods

	public void push(int element) {
		Stack current = this.stacks;

		while (current.isFull() && current.getNext() != null) {
			current = current.getNext();
		}

		current.push(element);
	}

	public int top() {
		Stack current = this.stacks;

		int top = current.top();

		while (current.getNext() != null) {
			current = current.getNext();
			top = current.top();
		}

		return top;
	}

	public int pop() {
		if (stacks.isEmpty()) {
			return Integer.MIN_VALUE;
		}

		return stacks.pop();
	}

	@Override
	public String toString() {
		return stacks.toString();
	}

	public static void main(String[] args) {
		MultiStack ms = new MultiStack();

		int bound = 30000001;
		for (int i = 0; i <= bound; i++) {
			ms.push(i);
		}

		ms.pop();

		System.out.println("<======= Current MultiStack =======>");
		System.out.println(ms);
		System.out.println("<======= MultiStack Top Test =======>");
		System.out.println(ms.top() == --bound);

	}

}
