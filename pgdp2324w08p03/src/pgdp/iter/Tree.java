package pgdp.iter;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Tree<T extends Comparable<T>> implements Iterable<T> {
    private Element root;

    public void insert(T value) {
        Objects.requireNonNull(value);
        if (root == null) {
            root = new Element(value);
        }
        else {
            root.insert(value);
        }
    }

    public void setRoot(Element root) {
        this.root = root;
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterator(this.root);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        Iterator it = new Iterator(root);

        while (it.hasNext()) {
            out.append(it.next().toString());
        }

        return out.toString();
    }

    public class Element {
        private T value;
        private Element left;
        private Element right;

        public Element(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Element getLeft() {
            return left;
        }

        public void setLeft(Element left) {
            this.left = left;
        }

        public Element getRight() {
            return right;
        }

        public void insert(T newValue) {
            if (newValue.compareTo(this.value) <= 0) {
                if (left == null) {
                    left = new Element(newValue);
                }
                else {
                    left.insert(newValue);
                }
            }
            else if (newValue.compareTo(this.value) > 0) {
                if (right == null) {
                    right = new Element(newValue);
                }
                else {
                    right.insert(newValue);
                }
            }
        }

        public void setRight(Element right) {
            this.right = right;
        }

        @Override
        public String toString() {
           return value.toString();
        }
    }

    public class Iterator implements java.util.Iterator<T> {
		private Element e;
        private Iterator left;
        private Iterator right;
        private boolean ownVal = false;
		
        public Iterator(Element e) {
            this.e = e;

            if (e != null) {
                this.left = new Iterator(e.left);
                this.right = new Iterator(e.right);
            }
        }

        @Override
        public boolean hasNext() {
            if (e == null) {
                return false;
            }

            return left.hasNext() || !ownVal || right.hasNext();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (left.hasNext()) {
                return left.next();
            } else if (!ownVal) {
                ownVal = true;
                return e.value;
            } else {
                return right.next();
            }
        }
    }

    public static void main(String[] args) {
        Tree<Integer> testTree = new Tree<>();

        testTree.insert(0);
        testTree.insert(1);
        testTree.insert(2);

        System.out.println(testTree.iterator().hasNext());

        for (Integer integer : testTree) {
            System.out.println(integer.toString());
        }
    }

}