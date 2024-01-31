package pgdp.io;

import java.io.*;

public class PenguinRepository implements Repository<Penguin> {
    @Override
    public void save(Penguin p, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write(p.name() + "\n");
            bw.write(p.age() + "\n");
            bw.write(p.weight() + "\n");
            bw.close();
        } catch (IOException e) {
            System.err.println("Error while trying to save penguin");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Penguin load(String filename) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String name = br.readLine();
            int age = Integer.parseInt(br.readLine());
            double weight = Double.parseDouble(br.readLine());

            return new Penguin(name, age, weight);
        } catch (IOException e) {
            System.err.println("Error while trying to load penguin!");
            throw new RuntimeException(e);
        }
    }
}
