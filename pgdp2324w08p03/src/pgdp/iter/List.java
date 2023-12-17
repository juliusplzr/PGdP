package pgdp.iter;

import java.util.NoSuchElementException;

public class List<T> implements Iterable<T> {

    private Element first;
    private Element last;

    void insert(T value) {
        if (first == null) {
            first = new Element(value);
            last = first;
        } else {
            var n = new Element(value);
            last.setNext(n);
            last = n;
        }
    }

    @Override
    public java.util.Iterator<T> iterator() {
		return new Iterator(first);
    }

    public class Element {
        private T value;
        private Element next;

        Element(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }
    }

    public class Iterator implements java.util.Iterator<T> {
		private Element current;
		
        public Iterator(Element e) {
            this.current = e;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            try {
                if (hasNext()) {
                    T val = current.value;
                    current = current.next;
                    return val;
                } else {
                    throw new NoSuchElementException();
                }
            }

            catch (Exception NoSuchElementException) {
                System.out.println("Empty iterator!");
                return null;
            }
        }
    }

}