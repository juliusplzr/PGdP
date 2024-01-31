package pgdp.io;

public class Main {

    public static void main(String[] args) {
        Penguin p = new Penguin("Tux", 10, 20.0);
        PenguinRepository pr = new PenguinRepository();
        pr.save(p, "tux.txt");
        Penguin p2 = pr.load("tux.txt");
        System.out.println(p2);
    }
}
