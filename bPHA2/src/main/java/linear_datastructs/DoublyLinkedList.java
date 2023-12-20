package linear_datastructs;

public class DoublyLinkedList<T> {
    private DoublyLinkedListElement<T> head;
    private DoublyLinkedListElement<T> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public void append(T info) {
        DoublyLinkedListElement<T> element = new DoublyLinkedListElement<>(info);
        if(size == 0) {
            head = tail = element;
        } else {
            tail.next = element;
            element.prev = tail;
            tail = element;
        }

        size++;
    }

    public int size() {
        return size;
    }

    /**
     * Internal getter for a specific list element
     *
     * @param index the position to get, assumed to be valid
     * @return the internal element at the given position
     */
    private DoublyLinkedListElement<T> getElement(int index) {
        if(index < 0 || head == null) {
            return null;
        }

        return head.getElement(index);
    }

    public T get(int index) {
        if (index < 0 || size <= index) {
            System.out.println("Position ist größer als Liste!");
            throw new IllegalArgumentException();
        }
        return getElement(index).getInfo();
    }

    public void remove(int index) {
        if (size <= index || index < 0) {
            // error if pos is invalid
            System.out.println("Position ist größer als Liste!");
        } else {
            // otherwise link element index-1 with index+1
            DoublyLinkedListElement<T> toRemove = getElement(index);

            // it might delete the head, check if prev exists
            if (toRemove.prev != null) {
                toRemove.prev.next = toRemove.next;
            } else {
                head = toRemove.next;
            }

            // it might be the tail as well, check if next exists
            if (toRemove.next != null) {
                toRemove.next.prev = toRemove.prev;
            } else {
                tail = toRemove.prev;
            }
            size--;
        }
    }

    public void appendList(DoublyLinkedList<T> other) {
        // if this list has elements, append
        if (size != 0) {
            tail.next = other.head;
            // otherwise set new head
        } else {
            head = other.head;
        }
        // if the other list is empty nothing needs to be done
        if (other.size != 0) {
            other.head.prev = tail;
            tail = other.tail;
            size += other.size;
        }
    }

    @Override
    public String toString() {
        if(head == null) {
            return "";
        }

        return head.toString();
    }

    public boolean isEqual(DoublyLinkedList<T> other) {
        if (other == null) {
            return false;
        }

        if(head == null) {
            return other.head == null;
        }

        return head.isEqual(other.head);
    }

    public DoublyLinkedList<T> copy() {
        DoublyLinkedList<T> copy = new DoublyLinkedList<>();
        if(head == null) {
            return copy;
        }

        return head.copy(copy);
    }

    public static class DoublyLinkedListElement<T> {
        private T info;

        public T getInfo() {
            return info;
        }

        // These attributes are public because the elements are only supposed to be
        // used by the list which keeps the invariants. The list does not expose its
        // elements to the outside
        public DoublyLinkedListElement<T> next;
        public DoublyLinkedListElement<T> prev;

        public DoublyLinkedListElement(T startInfo) {
            info = startInfo;
            next = null;
            prev = null;
        }

        public DoublyLinkedListElement<T> getElement(int index) {
            if (index == 0) {
                return this;
            }

            if (next == null) {
                return null;
            }

            return next.getElement(index - 1);
        }

        public String toString() {
            String listAsString = "" + info;
            if(next != null) {
                listAsString += "," + next.toString();
            }
            return listAsString;
        }

        public boolean isEqual(DoublyLinkedListElement<T> other) {
            return other != null && info == other.info
                    && (next == null && other.next == null || next != null && next.isEqual(other.next));
        }

        public DoublyLinkedList<T> copy(DoublyLinkedList<T> copySoFar) {
            copySoFar.append(info);
            if(next == null) {
                return copySoFar;
            }
            return next.copy(copySoFar);
        }
    }
}
