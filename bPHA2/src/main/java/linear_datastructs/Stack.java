package linear_datastructs;

import java.util.ArrayList;

public class Stack<T> {
    private int pointer;
    private ArrayList<T> queue;

    public Stack() {
        pointer = -1;
        queue = new ArrayList<T>(4);
    }

    public boolean isEmpty() {
        return pointer == -1;
    }

    public T pop() {
        T result = queue.get(pointer);

        if (pointer == (queue.size() / 4) && pointer >= 2) {
            ArrayList<T> nQueue = new ArrayList<>(pointer / 2);

            for (int i = 0; i < pointer; i++) {
                nQueue.set(i, queue.get(i));
            }

            queue = nQueue;
        }

        return result;
    }

    public T peek() {
        return queue.get(pointer);
    }

    public void push(T element) {
        ++pointer;

        if (pointer == queue.size()) {
            ArrayList<T> nQueue = new ArrayList<>(2 * pointer);

            for (int i = 0; i < queue.size(); i++) {
                nQueue.set(i, queue.get(i));
            }

            queue = nQueue;
        }

        queue.set(pointer, element);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "";
        }

        StringBuilder out = new StringBuilder();

        out.append("[");

        while (!isEmpty()) {
            out.append(this.pop().toString()).append(", ");
        }

        return out.substring(0, out.lastIndexOf(",")) + "]";
    }
}
