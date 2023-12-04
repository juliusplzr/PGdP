package pgdp.oopwarmup.pingutils;

import java.util.Objects;
public class DataNormalizer {
    public static Serializable[] distinct(Serializable[] data) {
        Serializable[] outArrayWithPadding = new Serializable[data.length];
        int firstUnusedPosition = 0;

        for (Serializable datum : data) {
            if (!containsBeforeIndex(outArrayWithPadding, datum, firstUnusedPosition)) {
                outArrayWithPadding[firstUnusedPosition] = datum;
                firstUnusedPosition++;
            }
        }

        return resize(outArrayWithPadding, firstUnusedPosition);
    }

    // <=========================== Helpers =================================>
    public static Serializable[] resize(Serializable[] a, int length) {
        if (length <= 0) {
            return new Serializable[0];
        }

        Serializable[] resized = new Serializable[length];

        for (int i = 0; i < length && i < a.length; i++) {
            resized[i] = a[i];
        }

        return resized;
    }

    private static boolean containsBeforeIndex(Serializable[] a, Serializable serializable, int index) {
        for (int i = 0; i < index; i++) {
            if (a[i].equals(serializable)) {
                return true;
            }
        }

        return false;
    }
}