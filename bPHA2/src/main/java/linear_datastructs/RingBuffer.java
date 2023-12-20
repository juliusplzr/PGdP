package linear_datastructs;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class RingBuffer<T> {

    private ArrayList<T> mem;
    private int in;
    private int out;
    private int stored;

    RingBuffer(int capacity) {
        mem = new ArrayList<>(capacity);
        in = 0;
        out = 0;
        stored = 0;
    }

    public boolean isEmpty() {
        return stored == 0;
    }

    public boolean isFull() {
        return stored == mem.size();
    }

    public boolean put(T val) {
        if (isFull()) {
            return false;
        }

        mem.set(in++, val);
        in %= mem.size();
        stored++;
        return true;
    }

    public T get() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T val = mem.get(out++);
        out %= mem.size();
        stored--;
        return val;
    }

    @Override
    public String toString() {
        StringBuilder memtoString = new StringBuilder();
        memtoString.append("[");

        for (T e : mem) {
            memtoString.append(e.toString()).append(", ");
        }

        memtoString.substring(0, memtoString.lastIndexOf(","));

        StringBuilder sb = new StringBuilder();
        sb.append("RingBuffer := { capacity = ").append(mem.size()).append(", out = ").append(out).append(", in = ")
                .append(in).append(", stored = ").append(stored).append(", mem = ").append(memtoString)
                .append(", buffer = [");
        if (!isEmpty()) {
            if (in >= 0 || in < mem.size()) {
                int i = out;
                do {
                    sb.append(mem.get(i)).append(", ");
                    i = (i + 1) % mem.size();
                } while (i != in);
                sb.setLength(sb.length() - 2);
            } else {
                sb.append("Error: Field 'in' is <").append(in).append(">, which is out of bounds for an array of length ").append(mem.size());
            }
        }
        sb.append("] }");
        return sb.toString();
    }
}
