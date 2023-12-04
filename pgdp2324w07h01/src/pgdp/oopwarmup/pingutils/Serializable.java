package pgdp.oopwarmup.pingutils;

// Note that this is a custom interface, not the java.io.Serializable interface.
public interface Serializable {
    String serialize();

    boolean equals(Serializable other);
}
