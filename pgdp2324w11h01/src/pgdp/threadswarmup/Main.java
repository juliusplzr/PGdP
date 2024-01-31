package pgdp.threadswarmup;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // The following method is there to help you testing
        // You can change the number of nodes in the tree
        // Lower numbers will make it easier keep track of what is happening.
        // You can modify the random seed inside the generateTreeWithRandomValues method,
        // to get the same tree every time you run the program.
        Node root = generateTreeWithRandomValues(1000);

        // Time the sequential sum
        long start = System.currentTimeMillis();
        System.out.println("Sum: " + root.sum());
        long end = System.currentTimeMillis();
        System.out.println("Sequential sum took " + (end - start) + "ms");

        // Time the parallel sum
        start = System.currentTimeMillis();
        System.out.println("Sum with threads found: " + root.sumWithThreads(1));
        end = System.currentTimeMillis();
        System.out.println("Parallel sum took " + (end - start) + "ms");
    }

    public static long getRuntime(int numberOfThreads, int numberOfNodes) {
        Node root = generateTreeWithRandomValues(numberOfNodes);

        // Time the parallel sum
        long start = System.currentTimeMillis();
        root.sumWithThreads(numberOfThreads);
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static Node generateTreeWithRandomValues(int numberOfNodes) {
        int valueLimit = 10;

        // The random object is the source for the random values
        // If you initialize the object with a seed, you will always get the same values
        // This is useful for testing because you will get the same tree everytime you run the code.
        // Example: Random random = new Random(1234);
        Random random = new Random();

        // Note that this is a First-in-last-out queue
        Queue<Node> nodesWithoutChildren = new LinkedList<>();
        int rootValue = Math.abs(random.nextInt()) % valueLimit;
        Node root = new Node(rootValue);
        nodesWithoutChildren.add(root);
        int nodesAdded = 1;
        while (nodesAdded < numberOfNodes) {
            Node currentParent = nodesWithoutChildren.poll();
            if (currentParent == null) {
                // This should never happen
                throw new RuntimeException("Not enough nodes in queue");
            }

            int leftValue = Math.abs(random.nextInt()) % valueLimit;
            Node left = new Node(leftValue);
            currentParent.setLeft(left);
            nodesWithoutChildren.add(left);
            nodesAdded++;

            if (nodesAdded < numberOfNodes) {
                int rightValue = Math.abs(random.nextInt()) % valueLimit;
                Node right = new Node(rightValue);
                currentParent.setRight(right);
                nodesWithoutChildren.add(right);
                nodesAdded++;
            }
        }
        return root;
    }

    public static Node getAnUnoptimalTree(int numberOfNodes) {
        // Generate a tree that is practically a linked list
        // This is very inefficient for out our parallel sum
        // 1. We create a thread for every node, one after another
        // so we do not really have any real parallelism
        // 2. The way we calculate the number of remaining threads,
        // meaning half of the current number of threads is unused.
        Node root = new Node(1);
        Node current = root;
        for(int i = 0; i < numberOfNodes - 1; i++) {
            Node next = new Node(1);
            root.setLeft(current);
            current = next;
        }
        return root;
    }
}
