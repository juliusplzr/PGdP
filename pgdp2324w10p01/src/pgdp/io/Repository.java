package pgdp.io;

public interface Repository<T> {
    public void save(T t, String filename);

    public T load(String filename);
}
