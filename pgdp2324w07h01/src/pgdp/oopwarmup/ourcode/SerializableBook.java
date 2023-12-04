package pgdp.oopwarmup.ourcode;

import pgdp.oopwarmup.pingutils.Serializable;
import pgdp.oopwarmup.foreigncode.*;

import java.lang.String;

public class SerializableBook extends Book implements Serializable {
    private String title;
    private String author;

    public SerializableBook(String title, String author) {
        super(title, author);
    }

    public String serialize() {
        return "SerializableBook, " + super.getTitle() + ", " + super.getAuthor();
    }

    @Override
    public boolean equals(Serializable other) {
        if (other instanceof SerializableBook) {
            String otherSerialized = other.serialize();

            String otherTitle = otherSerialized.substring(18, otherSerialized.lastIndexOf(','));
            String otherAuthor = otherSerialized.substring(otherSerialized.lastIndexOf(',') + 2);

            String thisTitle = super.getTitle();
            String thisAuthor = super.getAuthor();

            return otherTitle.equals(thisTitle) && otherAuthor.equals(thisAuthor);
        }

        return false;
    }

    public static void main(String[] args) {
        SerializableBook sb = new SerializableBook("Harry", "Potter");
        SerializableBook sb1 = new SerializableBook("Harry", "Potter");
        SerializableBook sb2 = new SerializableBook("Harry", "potter");
        System.out.println(sb.serialize());
        System.out.println(sb.equals(sb2));
    }
}
