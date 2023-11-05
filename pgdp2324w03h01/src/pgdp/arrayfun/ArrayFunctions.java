package pgdp.arrayfun;

public class ArrayFunctions {

    protected ArrayFunctions() {
        throw new IllegalStateException("Don't create objects of type 'ArrayFunctions'!");
    }

    public static void main(String[] args) {
        //example call
        //System.out.println(Arrays.toString(zip(new int[]{1, 3}, new int[]{2, 4,})));
    }

    /** Berechnet für das übergebene Array die Summe der Quadrate der Einträge.
     *  Gibt dabei einen Fehler aus und -1 zurück, wenn ein Overflow entsteht.
     *
     * @param array Ein beliebiges Integer-Array.
     * @return Die Summe der Quadrate, wenn diese in einen 'long' passt, -1 sonst.
     */
    public static long sumOfSquares(int[] array) {
        long sum = 0;
        int i = 0;

        while (i < array.length) {
            long sq = (long) array[i] * (long) array[i];
            if (sq > Long.MAX_VALUE - sum) {
                System.out.println("Overflow!");
                return -1;
            }
            sum += sq;
            i++;
        }

        return sum;
    }


    /** Methode, die zwei Arrays zu einem verbindet, indem sie abwechselnd Einträge des ersten und des zweiten Input-
     *  Arrays verwendet.
     *
     * @param a Ein beliebiges Integer-Array.
     * @param b Ein beliebiges Integer-Array.
     * @return 'a' und 'b' zusammengezipped.
     */
    public static int[] zip(int[] a, int[] b) {
        if (a.length == 0 && b.length == 0) {
          return new int[0];
        }
        int[] zipped = new int [a.length + b.length];

        int[] min = (a.length < b.length) ? a : b;
        int[] max = (a.length >= b.length) ? a : b;

        zipped[0] = a[0];
        zipped[1] = b[0];

        int i = 2;

        while (i/2 < min.length) {
            zipped[i] = i % 2 == 0 ? a[i/2] : b[i/2];
            i++;
        }

        for (int j = i; j < a.length + b.length; j++) {
            zipped[j] = max[j - min.length];
        }
        return zipped;
    }

    /** Methode, die eine beliebige Zahl an Arrays (dargestellt als Array von Arrays) zu einem einzigen Array verbindet,
     *  indem sie abwechselnd von jedem Array einen Eintrag nimmt, bis alle aufgebraucht sind.
     *
     * @param arrays Array von Integer-Arrays
     * @return Die Arrays in 'arrays' zusammengezipped
     */
    public static int[] zipMany(int[][] arrays) {
        if (arrays.length == 0) {
            return new int[0];
        }
        int totalLength = 0;
        int maxLength = 0;

        for (int[] array : arrays) {
            if (array.length > maxLength) {
                maxLength = array.length;
            }
            totalLength += array.length;
        }

        int[] zipped = new int[totalLength];
        int pos = 0;

        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < arrays.length; j++) {
                if (i < arrays[j].length) {
                    zipped[pos] = arrays[j][i];
                    pos++;
                }
            }
        }

        return zipped;
    }

    /** Behält aus dem übergebenen Array nur die Einträge, die innerhalb der übergebenen Grenzen liegen.
     *  Gibt das Ergebnis als neues Array zurück.
     *
     * @param array Ein beliebiges Integer-Array
     * @param min Ein beliebiger Integer
     * @param max Ein beliebiger Integer
     * @return Das gefilterte Array
     */
    public static int[] filter(int[] array,int min,int max) {
        if (array.length == 0) {
            return new int[0];
        }
        if (max < min) {
            return new int[0];
        }

        int fulfill = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] <= max && array[i] >= min) {
                fulfill++;
            }
        }

        int[] filtered = new int [fulfill];
        int first = 0;

        while (first < array.length) {
            if (array[first] <= max && array[first] >= min) {
                break;
            }
            first++;
        }

        filtered[0] = array[first];

        int last = first;
        for (int i = 1; i < fulfill; i++) {
            int j = last + 1;

            while (j < array.length) {
                if (array[j] <= max && array[j] >= min) {
                    filtered[i] = array[j];
                    last = j;
                    break;
                }
                j++;
            }
        }
        return filtered;
    }

    /** Rotiert das übergebene Array um die übergebene Anzahl an Schritten nach rechts.
     *  Das Array wird In-Place rotiert. Es gibt keine Rückgabe.
     *
     * @param array Ein beliebiges Integer-Array
     * @param amount Ein beliebiger Integer
     */
    public static void rotate(int[] array, int amount) {
        if (array.length == 0) {
            return;
        }

        if (amount < 0) {
            while (amount < 0) {
                amount += array.length;
            }
        }

        int[] rotated = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            rotated[(i + amount) % array.length] = array[i];
        }

        for (int i = 0; i < array.length; i++) {
            array[i] = rotated[i];
        }
    }

    /** Zählt die Anzahl an Vorkommen jeder Zahl im übergebenen Array, die in diesem mindestens einmal vorkommt.
     *  Die Rückgabe erfolgt über ein 2D-Array, bei dem jedes innere Array aus zwei Einträgen besteht: Einer Zahl,
     *  die im übergebenen Array vorkommt sowie der Anzahl an Vorkommen dieser.
     *  Für jede im übergebenen Array vorkommenden Zahl gibt es ein solches inneres Array.
     *  Diese tauchen im Rückgabewert in der gleichen Reihenfolge auf, in der die jeweils ersten Vorkommen der Zahlen
     *  im übergebenen Array auftauchen.
     *
     * @param array Ein beliebiges Integer-Array
     * @return Das Array mit den Vielfachheiten der einzelnen Zahlen, wiederum als Integer-Arrays mit zwei Einträgen dargestellt.
     */
    public static int[][] quantities(int[] array) {
        // Edge case
        if (array.length == 0) {
            return new int[0][0];
        }
        int distinct = 0;

        // Number of distinct elements in array
        for (int i = 0; i < array.length; i++) {
            int j;

            for (j = 0; j < i; j++) {
                if (array[i] == array[j]) {
                    break;
                }
            }

            if (i == j) {
                distinct++;
            }
        }

        // Create array of length "distinct"
        int[] distinct_values = new int[distinct];

        // Array containing ONLY DISTINCT elements
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            boolean included = false;

            for (int j = 0; j < i; j++) {
                if (array[j] == array[i]) {
                    included = true;
                    break;
                }
            }

            if (!included) {
                distinct_values[index] = array[i];
                index++;
            }
        }

        // Count occurrences of distinct values and include count in
        // quantities array
        int[][] quantities = new int[distinct][2];
        int pos = 0;

        for (int j = 0; j < distinct; j++) {
            int count = 0;

            for (int i : array) {
                if (distinct_values[j] == i) {
                    count++;
                }
            }
            quantities[pos] = new int[] {distinct_values[j], count};
            pos++;
        }

        return quantities;
    }
    // <|=================== Helpers ===================|>
}