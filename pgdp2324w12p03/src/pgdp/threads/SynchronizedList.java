package pgdp.threads;

import java.util.LinkedList;
import java.util.List;

public class SynchronizedList<T> {
    private List<T> innerList = new LinkedList<>();
    private RW lock = new RW();

    public void add(int index, T e) throws InterruptedException {
        lock.startWrite();
        innerList.add(index, e);
        lock.endWrite();
    }

    public T remove(int index) throws InterruptedException {
        lock.startWrite();
        T result = innerList.remove(index);
        lock.endWrite();
        return result;
    }

    public T get(int index) throws InterruptedException {
        lock.startRead();
        T result = innerList.get(index);
        lock.endRead();
        return result;
    }

    public boolean contains(T e) throws InterruptedException {
        lock.startRead();
        boolean result = innerList.contains(e);
        lock.endRead();
        return result;
    }
}