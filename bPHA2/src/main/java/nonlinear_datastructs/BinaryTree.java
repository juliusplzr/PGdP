package nonlinear_datastructs;

import linear_datastructs.List;
import linear_datastructs.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTree<T extends Comparable<T>> implements Iterable<T> {
    private BSTNode<T> root;
    public BinaryTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size();
    }

    public boolean contains(T value) {
        if (isEmpty()) {
            return false;
        }
        return root.contains(value);
    }

    public void insert(T value) {
        if (value == null) {
            return;
        }
        if (isEmpty()) {
            root = new BSTNode<T>(value);
        } else {
            root.insert(value);
        }
    }

    public List<T> toList() {
        if (isEmpty()) {
            return new List<>();
        }

        return root.toList(Order.IN);
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        } else {
            return root.toString();
        }
    }

    public enum Order {
        PRE, POST, IN
    }

    public abstract class Node<T> {

        private T value;
        private Node<T>[] children;

        @SafeVarargs
        public Node(T value, Node<T>... nodes) {
            this.value = value;
            children = nodes;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }


        public Node<T>[] getChildren() {
            return children;
        }

        public Node<T> getChild(int i) {
            if (i < 0 || children.length <= i) {
                return null;
            }
            return children[i];
        }

        public void setChild(int i, Node<T> c) {
            if (i < 0 || children.length <= i) {
                return;
            }
            children[i] = c;
        }

        public boolean isLeaf() {
            for (Node<T> node : children) {
                if (node != null) {
                    return false;
                }
            }
            return true;
        }

        public int height() {
            int height = 0;
            for (Node<T> node : children) {
                if (node != null) {
                    int nodeHeight = node.height();
                    height = Math.max(height, nodeHeight);
                }
            }
            return height + 1;
        }

        public int size() {
            int size = 0;
            for (Node<T> node : children) {
                if (node != null) {
                    size += node.size();
                }
            }
            return size + 1;
        }

        public abstract List<T> toList(Order order);
    }

    public class BinaryNode<T> extends Node<T> {

        public BinaryNode(T value, BinaryNode<T> left, BinaryNode<T> right) {
            super(value, left, right);
        }

        public BinaryNode(T value) {
            super(value, null, null);
        }

        public BinaryNode<T> getLeft() {
            return (BinaryNode<T>) getChild(0);
        }

        public BinaryNode<T> getRight() {
            return (BinaryNode<T>) getChild(1);
        }

        public void setLeft(BinaryNode<T> left) {
            setChild(0, left);
        }

        public void setRight(BinaryNode<T> right) {
            setChild(1, right);
        }

        @Override
        public List<T> toList(Order order) {
            List<T> list = new List<>();
            List<T> leftList = new List<>();
            List<T> rightList = new List<>();

            if (getLeft() != null) {
                leftList = getLeft().toList(order);
            }
            if (getRight() != null) {
                rightList = getRight().toList(order);
            }

            switch (order) {
                case IN:
                    list.addAll(leftList);
                    list.add(getValue());
                    list.addAll(rightList);
                    break;
                case POST:
                    list.addAll(leftList);
                    list.addAll(rightList);
                    list.add(getValue());
                    break;
                case PRE:
                    list.add(getValue());
                    list.addAll(leftList);
                    list.addAll(rightList);
                    break;
                default:
                    break;
            }
            return list;
        }

        public String toString(Order order) {
            if (isLeaf()) {
                return "[" + getValue().toString() + "]";
            }

            String leftStr = "-";
            String rightStr = "-";

            if (getLeft() != null) {
                leftStr = getLeft().toString(order);
            }
            if (getRight() != null) {
                rightStr = getRight().toString(order);
            }

            switch (order) {
                case IN:
                    return "[" + leftStr + ", " + getValue().toString() + ", " + rightStr + "]";
                case POST:
                    return "[" + leftStr + ", " + rightStr + ", " + getValue().toString() + "]";
                case PRE:
                    return "[" + getValue().toString() + ", " + leftStr + ", " + rightStr + "]";
                default:
                    return "SOMETHING WENT WRONG!";
            }
        }

        @Override
        public String toString() {
            return toString(Order.IN);
        }
    }

    public class BSTNode<T extends Comparable<T>> extends BinaryNode<T> {

        public BSTNode(T value, BSTNode<T> left, BSTNode<T> right) {
            super(value, left, right);
        }

        public BSTNode(T value) {
            super(value, null, null);
        }

        public boolean contains(T value) {
            if (getValue().equals(value)) {
                return true;
            } else if (value.compareTo(getValue()) < 0) {
                if (getLeft() == null) {
                    return false;
                } else {
                    return ((BSTNode<T>) getLeft()).contains(value);
                }
            } else {
                if (getRight() == null) {
                    return false;
                } else {
                    return ((BSTNode<T>) getRight()).contains(value);
                }
            }
        }

        public void insert(T value) {
            if (getValue().equals(value)) {
                return;
            } else if (value.compareTo(getValue()) < 0) {
                if (getLeft() == null) {
                    setLeft(new BSTNode<T>(value));
                } else {
                    ((BSTNode<T>) getLeft()).insert(value);
                }
            } else {
                if (getRight() == null) {
                    setRight(new BSTNode<T>(value));
                } else {
                    ((BSTNode<T>) getRight()).insert(value);
                }
            }
        }

    }

    public Iterator<T> iterator() {
        return new InOrderIterator<>(this.root);
    }

    public class InOrderIterator<T extends Comparable<T>> implements Iterator<T> {
        private Stack<BinaryNode<T>> traversal;

        public InOrderIterator(BinaryNode<T> start) {
            traversal = new Stack<>();
            moveLeft(start);
        }

        @Override
        public boolean hasNext() {
            return !traversal.isEmpty();
        }

        private void moveLeft(BinaryNode<T> current) {
            while (current != null) {
                traversal.push(current);
                current = current.getLeft();
            }
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            BinaryNode<T> next = traversal.pop();

            if (next.getRight() != null) {
                moveLeft(next.getRight());
            }

            return next.getValue();
        }
    }

    public class PostOrderIterator<T> implements Iterator<T> {
        private Stack<BinaryNode<T>> traversal;
        private BinaryNode<T> prev;

        public PostOrderIterator(BinaryNode<T> start) {
            if (start != null) {
                traversal = new Stack<>();
                recurse(start);
                prev = traversal.peek();
            } else {
                throw new IllegalArgumentException();
            }
        }

        @Override
        public boolean hasNext() {
            return !traversal.isEmpty();
        }

        private void recurse(BinaryNode<T> node) {
            if (node == null) {
                return;
            }

            while (node != null) {
                traversal.push(node);
                node = node.getLeft();
            }

            recurse(traversal.peek().getRight());
        }

        @Override
        public T next() {
            if (!(traversal.peek().getRight().equals(this.prev))) {
                recurse(traversal.peek().getRight());
            }

            BinaryNode<T> next = traversal.pop();
            this.prev = next;
            return next.getValue();
        }
    }

    public class PreOrderIterator<T> implements Iterator<T> {
        private Stack<BinaryNode<T>> traversal;

        public PreOrderIterator(BinaryNode<T> start) {
            traversal = new Stack<>();

            if (start != null) {
                traversal.push(start);
            } else {
                throw new NullPointerException();
            }
        }

        @Override
        public boolean hasNext() {
            return !traversal.isEmpty();
        }

        @Override
        public T next() {
            BinaryNode<T> next = traversal.pop();

            if (next.getRight() != null) {
                traversal.push(next.getRight());
            }

            if (next.getLeft() != null) {
                traversal.push(next.getLeft());
            }

            return next.getValue();
        }
    }

    public static void main(String[] args) {
        BinaryTree<Integer> bt = new BinaryTree<>();

        bt.insert(2);
    }
}
