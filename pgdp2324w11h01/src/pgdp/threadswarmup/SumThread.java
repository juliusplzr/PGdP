package pgdp.threadswarmup;

import java.util.Optional;

public class SumThread extends Thread {
    private Optional<Integer> sum;

    private Node node;

    private int remainingThreads;

    SumThread leftThread;
    SumThread rightThread;


    public SumThread(Node node, int remainingThreads) {
        sum = Optional.empty();
        this.node = node;
        this.remainingThreads = remainingThreads;
    }

    public Optional<Integer> getSum() {
        return sum;
    }

    public void run() {
        int calculatingSum = node.getValue();
        startChildThreads();

        if (leftThread == null) {
            if (node.getLeft().isPresent()) {
                calculatingSum += node.getLeft().get().sum();
            }
        }
        else {
            try {
                leftThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            calculatingSum += leftThread.getSum().get();
        }

        if (rightThread == null) {
            if (node.getRight().isPresent()) {
                calculatingSum += node.getRight().get().sum();
            }
        }
        else {
            try {
                rightThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            calculatingSum += rightThread.getSum().get();
        }

        sum = Optional.of(calculatingSum);
    }

    protected void startChildThreads() {
        int numberOfLeftThreads = leftThreadCount();
        int numberOfRightThreads = remainingThreads - 1 - numberOfLeftThreads;

        if (numberOfLeftThreads > 0 && node.getLeft().isPresent()) {
            leftThread = new SumThread(node.getLeft().get(), numberOfLeftThreads);
            leftThread.start();
        }

        if (numberOfRightThreads > 0 && node.getRight().isPresent()) {
            rightThread = new SumThread(node.getRight().get(), numberOfRightThreads);
            rightThread.start();
        }
    }

    protected int leftThreadCount() {
        int threadsForChildren = remainingThreads - 1;
        if (node.getLeft().isEmpty()) {
            return 0;
        }
        if (node.getRight().isPresent()) {
            return threadsForChildren / 2;
        }
        return threadsForChildren;
    }

}
