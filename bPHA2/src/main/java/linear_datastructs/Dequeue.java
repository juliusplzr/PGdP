package linear_datastructs;

import java.util.NoSuchElementException;

public class Dequeue<T> {
    private Element<T> head;
    private Element<T> tail;
    private int size;

    public Dequeue() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(T val) {
        if (isEmpty()) {
            head = new Element<>(val);
            tail = head;
        } else {
            head = new Element<>(val, head);
            head.next.prev = head;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T val = getFirst();
        if (size() == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return val;
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return head.val;
    }

    public void addLast(T val) {
        if (isEmpty()) {
            head = new Element<>(val);
            tail = head;
        } else {
            tail = new Element<>(tail, val);
            tail.prev.next = tail;
        }
        size++;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T val = getLast();
        if (size() == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return val;
    }

    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return tail.val;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Element<T> current = head; current != null; current = current.next) {
            sb.append(current.val).append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    private static class Element<T> {
        Element<T> prev;
        Element<T> next;
        T val;

        Element(T v) {
            val = v;
        }

        Element(T v, Element<T> n) {
            val = v;
            next = n;
        }

        Element(Element<T> p, T v) {
            val = v;
            prev = p;
        }
    }
}
