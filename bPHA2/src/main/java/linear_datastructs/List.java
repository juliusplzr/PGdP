package linear_datastructs;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<T> implements Iterable<T> {
    private ListElement<T> head;
    private ListElement<T> tail;
    private int size;

    public List () {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator(head);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /*
     * adds an element at the end of the list
     */
    public void add(T element) {
        if (head == null) {
            head = new ListElement<T>(element);
            tail = head;
        } else {
            tail.next = new ListElement<T>(element);
            tail = tail.next;
        }
        size++;
    }

    /*
     * adds an element at the specified index
     */
    public boolean add(int index, T value) {
        if (index < 0 || size < index) {
            return false;
        }

        if (head == null) {
            head = new ListElement<T>(value);
            tail = head;
        } else if (index == 0) {
            head = new ListElement<T>(value, head);
        } else if (index == size) {
            tail.next = new ListElement<T>(value);
            tail = tail.next;
        } else {
            ListElement<T> current = head;
            int currentIndex = 0;
            while (currentIndex < index - 1) {
                current = current.next;
                currentIndex++;
            }
            current.next = new ListElement<T>(value, current.next);
        }
        size++;
        return true;
    }

    /*
     * returns the value of the element at the specified index returns default value
     * (minimum value of an integer) iff. such an element does not exist.
     */
    public T get(int index) {
        if (index < 0 || size <= index) {
            throw new IndexOutOfBoundsException();
        }

        ListElement<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.value;
    }

    /*
     * removes the element at the specified index
     */
    public void remove(int index) {
        if (index < 0 || size <= index) {
            return;
        }

        if (size == 1) {
            head = null;
            tail = null;
        } else if (index == 0) {
            head = head.next;
        } else {
            ListElement<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
            if (index == size - 1) {
                tail = current;
            }
        }
        size--;
    }

    /*
    * Receive a String representation of List
    *
    * @return List as String with colon and space as delimiter
    */

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("[");

        ListElement<T> current = head;

        for (int i = 0; i < size; i++) {
            out.append(current.toString());

            if (i != size - 1) {
                out.append(", ");
            }

            current = current.next;
        }
        out.append("]");

        return out.toString();
    }

    // <================== Setters & Getters ==================>

    public ListElement<T> getHead() {
        return head;
    }

    public ListElement<T> getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }

    public void setHead(ListElement<T> head) {
        this.head = head;
    }

    public void setTail(ListElement<T> tail) {
        this.tail = tail;
    }

    public void setSize(int size) {
        this.size = size;
    }


    // <============================ Tests =============================>
    public static void main(String[] args) {
        List<String> l = new List<>();

        l.add("Hello");
        l.add("World");
        l.add("!");

        System.out.println(l);
    }

    public static class ListElement<T> {
        private T value;
        private ListElement<T> next;

        public ListElement(T value, ListElement<T> next) {
            this.value = value;
            this.next = next;
        }

        public ListElement(T value) {
            this.value = value;
            this.next = null;
        }

        public ListElement<T> getNext() {
            return next;
        }

        public T getValue() {
            return value;
        }

        public void setNext(ListElement<T> next) {
            this.next = next;
        }

        public void setValue(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public class ListIterator implements Iterator<T> {
        ListElement<T> current;

        public ListIterator(ListElement<T> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T val = current.value;
                current = current.next;
                return val;
            } else {
                throw new NoSuchElementException();
            }
        }
    }
}