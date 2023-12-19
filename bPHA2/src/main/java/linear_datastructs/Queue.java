package linear_datastructs;


import java.util.Iterator;
import java.lang.String;

public class Queue<T> implements Iterable<T> {
    private QueueElement<T> first;
    private QueueElement<T> last;
    private int size;

    public Queue() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void push(T value) {
        QueueElement<T> element = new QueueElement<>(value);
        if (isEmpty()) {
            first = element;
            last = first;
        } else {
            last.setNext(element);
            last = element;
        }

        size++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        T poppedValue = first.getValue();
        first = first.getNext();
        size--;
        return poppedValue;
    }

    public Iterator iterator() {
        return new Iterator<QueueElement<T>>() {
            QueueElement<T> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public QueueElement<T> next() {
                return current.next;
            }
        };
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder out = new StringBuilder();
        out.append("[");

        QueueElement<T> next = first;

        while (next != null){
            out.append(next.getValue()).append(", ");
            next = next.getNext();
        }

        return out.substring(0, out.lastIndexOf(",")) + "]";
    }

    public class QueueElement<T> {
        private T value;
        private QueueElement<T> next;

        public QueueElement(T value) {
            this.value = value;
        }

        public QueueElement(T value, QueueElement<T> next) {
            this.value = value;
            this.next = next;
        }

        public void setNext(QueueElement<T> next) {
            this.next = next;
        }

        public QueueElement<T> getNext() {
            return next;
        }

        public T getValue() {
            return value;
        }
    }
}
