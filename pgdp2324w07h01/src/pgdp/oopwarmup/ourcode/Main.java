package pgdp.oopwarmup.ourcode;

import pgdp.oopwarmup.pingutils.DataNormalizer;
import pgdp.oopwarmup.pingutils.Serializable;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Example code to test with
        // Commented out to prevent compile Errors

        Serializable[] things = new Serializable[6];
        things[0] = new SerializableBook("The Lord of the Rings", "J. R. R. Tolkien");
        things[1] = new SerializableBook("The Hobbit", "J. R. R. Tolkien");
        things[2] = new SerializableBook("The Hobbit", "J. R. R. Tolkien");
        things[3] = new SerializablePerson("Tom Bombadil", Integer.MAX_VALUE);
        things[4] = new SerializablePerson("Frodo Baggins", 50);
        things[5] = new SerializablePerson("Tom Bombadil", Integer.MAX_VALUE);
        printDistinct(things);

    }

    public static void printDistinct(Serializable[] things) {
        Serializable[] distinct = DataNormalizer.distinct(things);

        for (Serializable serializable : distinct) {
            System.out.println(serializable.serialize());
        }
    }
}
