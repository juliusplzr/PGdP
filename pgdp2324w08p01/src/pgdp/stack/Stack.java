package pgdp.stack;

import java.util.ArrayList;
import java.util.List;

public class Stack <T> {
    private List<T> stack;

    public Stack() {
        this.stack = new ArrayList<T>();
    }

    public void push(T t) {
        stack.add(t);
    }

    public T pop() {
        if (stack.isEmpty()) {
            return null;
        }

        return stack.remove(stack.size() - 1);
    }

    @Override
    public String toString() {
        return stack.toString();
    }

    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();

        stack.push("PGDP W07P04");
        System.out.println("Pushed: " + "PGDP W07P04");
        stack.push("PGDP W07H01");
        System.out.println("Pushed: " + "PGDP W07H01");
        stack.push("PGDP W07H02");
        System.out.println("Pushed: " + "PGDP W07H02");
        stack.push("DS HA");
        System.out.println("Pushed: " + "DS HA");

        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());

        stack.push("ERA HA");
        System.out.println("Pushed: " + "ERA HA");

        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());

        stack.push("PGDP W07P01");
        System.out.println("Pushed: " + "PGDP W07P01");
        stack.push("PGDP W07P02");
        System.out.println("Pushed: " + "PGDP W07P02");

        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());

        stack.push("PGDP W07P03");
        System.out.println("Pushed: " + "PGDP W07P03");

        System.out.println("Popped: " + stack.pop());
        System.out.println("Popped: " + stack.pop());

    }
}