package pgdp.messenger;

public class ListElement {
    private Message message;
    private ListElement next;

    public ListElement(Message message, ListElement next) {
        this.message = message;
        this.next = next;
    }

    public Message getMessage() {
        return message;
    }
    public ListElement getNext() {
        return next;
    }

    public void setNext(ListElement next) {
        this.next = next;
    }
}
