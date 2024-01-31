package pgdp.streams;

public class StreamToFor {

    // TODO 1.1
    public static void printSquares() {
        for (int i = 3; i >= -4; i--) {
            if (i == -4 || i == 3 || i == 1) {
                System.out.println(i * i);
            }
        }
    }
}
