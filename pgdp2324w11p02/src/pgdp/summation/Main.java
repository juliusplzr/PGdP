package pgdp.summation;

import java.util.stream.IntStream;

public class Main {
    public static long sumParallel(int[] array, int threadCount) {
        int stepSize = array.length / threadCount;
        int modulus = array.length % threadCount;

        ParallelSummer[] threads = new ParallelSummer[threadCount];

        // Threads erzeugen
        int currentLowerBound = 0;
        for(int i = 0; i < threadCount; i++) {
            // Die überbleibenden Slots beim ganzzahligen Teilen 'array.length / threadCount' werden gleichmäßig auf
            // die ersten 'array.length % threadCount' Threads verteilt.
            int amountToSum = stepSize;
            if(i < modulus) {
                amountToSum++;
            }

            threads[i] = new ParallelSummer(array, currentLowerBound, currentLowerBound + amountToSum);
            currentLowerBound += amountToSum;
        }

        // Threads starten
        for(Thread thread : threads) {
            thread.start();
        }

        // An dieser Stelle im Code warten, bis alle Threads durchgelaufen sind
        for(Thread thread : threads) {
            try {
                thread.join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Die 'result'-Variablen aus den nun mit dem Summieren fertigen ParallelSummern auslesen und summieren
        long result = 0;
        for(ParallelSummer summer : threads) {
            result += summer.getResult();
        }

        // Das Ergebnis zurückgeben
        return result;
    }

    public static void main(String[] args) {
        int[] toSum = IntStream.range(0, 1_000_000_000).toArray();

        long startTime = System.currentTimeMillis();

        // Setze threadCount auf verschiedene Werte und ermittele so
        //  1. ob du "korrekt" parallelisierst, also mit mehreren Threads die Berechnung tatsächlich schneller ist
        //  2. mit wie vielen Threads genau die Berechnung am schnellsten ist
        long result = sumParallel(toSum, 1);

        long finishTime = System.currentTimeMillis();

        System.out.println("Ergebnis der Berechnung: " + result);
        System.out.println("Dauer der Berechnung: " + ( finishTime - startTime ) + "ms");
    }
}
