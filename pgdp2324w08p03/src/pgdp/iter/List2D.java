package pgdp.iter;

import java.util.Iterator;

public class List2D<T> implements Iterable<T> {

    private List<List<T>> lists;

    public List2D() {
        lists = new List<>();
    }

    void insert(List<T> value) {
        lists.insert(value);
    }

    @Override
    public Iterator<T> iterator() {
        return new List2DIterator(lists);
    }

    public class List2DIterator implements Iterator<T> {
        private Iterator<T> elementIter;
        private Iterator<List<T>> listIter;

        public List2DIterator(List<List<T>> lists) {
            this.listIter = lists.iterator();

            while (listIter.hasNext()) {
                elementIter = listIter.next().iterator();

                if (elementIter.hasNext()) {
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return elementIter != null && elementIter.hasNext();
        }

        @Override
        public T next() {
            if (!elementIter.hasNext()) {
               return null;
            }

            T current = elementIter.next();

            while (!elementIter.hasNext() && listIter.hasNext()) {
                elementIter = listIter.next().iterator();
            }

            return current;
        }
    }
}
