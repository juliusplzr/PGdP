package b1f;

import java.util.Arrays;

public class ArraysUndPinguine {

    /**
     * Given two arrays of integers, return the greatest common element.
     * If there is no common element, return -1.
     * @return Greatest common element or -1 if none present
     */
    public static int greatestCommonElement(int[] array, int[] other) {
        if (array.length == 0 || other.length == 0) {
            return -1;
        }

        int intersectionCount = 0;
        for (int k : array) {
            for (int i : other) {
                if (k == i) {
                    intersectionCount++;
                }
            }
        }

        int[] intersection = new int[intersectionCount];
        int currentPos = 0;

        for (int k : array) {
            for (int i : other) {
                if (k == i) {
                    intersection[currentPos] = k;
                    currentPos++;
                }
            }
        }

        int max = 0;

        for (int k : intersection) {
            if (k > max) {
                max = k;
            }
        }

        return max;
    }

    /**
     * Given an array of integers, return a new ascending array by removing
     * all elements that are not strictly greater than all elements before them.
     * @param array
     * @return
     */
    public static int[] toAscending(int[] array) {
        if (array.length == 0) {
           return new int[0];
        }

        int ascendingCount = 1;
        int interimMax = array[0];

        for (int i = 1; i < array.length; i++) {
                if (array[i] > interimMax) {
                    interimMax = array[i];
                    ascendingCount++;
                }
        }

        int[] ascending = new int[ascendingCount];

        int tempMax = Integer.MIN_VALUE;
        int index = 0;

        for (int i = 0; i < array.length; i++) {
            if (array[i] > tempMax) {
                tempMax = array[i];
                ascending[index] = array[i];
                index++;
            }
        }

        return ascending;
    }


    /**
     * Given an array of names, return the number of teams when each team
     * consists of all names that start with the same letter.
     * @param names Array of names, where each name is an array of characters
     *              Names start with capital letters and consist only of letters
     *              Every name has at least one character.
     * @return The number of teams
     */
    public static int numberOfTeams(char[][] names) {
        char[][] onlyFirstLetters = new char[names.length][1];

        for (int i = 0; i < onlyFirstLetters.length; i++) {
            onlyFirstLetters[i] = removeRange(names[i], 1, names[i].length - 1);
        }

        char[] onlyFirstLettersLinearized = linearize(onlyFirstLetters);

        return distinct(onlyFirstLettersLinearized);
    }

    // <|=============================== Helper ===============================|>

    // From https://github.com/Knall64/someSmallArrayStuff/blob/main/Arrayz.java
    public static char[] linearize(char[][] a) {
        int len = 0;
        for (int i=0;i<a.length;i++){
            len = len + a[i].length;
        }


        char[] lin = new char[len];
        int cc = 0;

        for (int j = 0;j<a.length;j++){
            for (int k = 0; k<a[j].length;k++){
                lin[cc] = a[j][k];
                cc++;
            }
        }

        return lin;
    }

    // From https://github.com/sirjulzz/PGdP/blob/main/pgdp2324bPHA1/src/pgdp/bPHA1/bPHA1.java
    public static char[] removeRange(char[] array, int start, int end) {
        if (array.length == 0) {
            return new char[0];
        }

        if (start > end) {
            System.out.println("Index range empty!");
            return new char[0];
        }

        if (end > array.length - 1 || start < 0) {
            System.out.println("Invalid index range!");
            return new char[0];
        }

        char[] removed = new char[array.length - (end - start + 1)];

        for (int i = 0; i < start; i++) {
            removed[i] = array[i];
        }

        for (int i = end + 1; i < array.length; i++) {
            removed[i - (end - start + 1)] = array[i];
        }

        return removed;
    }

    // From https://github.com/sirjulzz/PGdP/blob/main/pgdp2324bPHA1/src/pgdp/bPHA1/bPHA1.java
    private static int distinct(char[] a) {
        if (a.length == 0) {
            return -1;
        }

        int distinctCount = 0;

        for (int i = 0; i < a.length; i++) {
            boolean included = false;
            for (int j = 0; j < i; j++) {
                if (a[j] == a[i]) {
                    included = true;
                    break;
                }
            }

            if (!included) {
                distinctCount++;
            }
        }

        return distinctCount;
    }


    /**
     * Given a field of points and a list of hits, return the total number of points.
     * Every field hat as at least one hit brings the number of points specified in
     * fieldPoints.
     * For every row and column that is completely hit, 5 points are added.
     * If all 4 players hit a field, 10 points are added.
     * @param fieldPoints 2-D array of points for every field. Every row has the same length.
     * @param hits 2-D array of hits. It has the exact same dimensions as fieldPoints.
     *             Every field is either 0 (no hit) or positive (number of hits).
     * @return
     */
    public static int pinguballPoints(int[][] fieldPoints, int[][] hits) {
        int points = 0;

        for (int i = 0; i < hits.length; i++) {
           for (int j = 0; j < hits[i].length; j++) {
               if (hits[i][j] >= 1) {
                   points += fieldPoints[i][j];
               }
           }
        }

        boolean allThrowsHit;
        int sumOfThrowsHit = 0;

        for (int i = 0; i < hits.length; i++) {
            for (int j = 0; j < hits[i].length; j++) {
                sumOfThrowsHit += hits[i][j];
            }
        }

        allThrowsHit = (sumOfThrowsHit == 4);

        points = allThrowsHit ? points + 10 : points;

        boolean completeRow = true;
        int rowAdditionalPoints = 0;

        for (int i = 0; i < hits.length; i++) {
            for (int j = 0; j < hits[i].length; j++) {
                if (hits[i][j] != 1) {
                    completeRow = false;
                    break;
                }
            }

            if (completeRow) {
                rowAdditionalPoints += 5;
            }
        }

        points += rowAdditionalPoints;

        boolean completeCol = true;
        int colAdditionalPoints = 0;

        int[][] hitsTranspose = transpose(hits);

        for (int i = 0; i < hitsTranspose.length; i++) {
            for (int j = 0; j < hitsTranspose[i].length; j++) {
                if (hitsTranspose[i][j] != 1) {
                    completeCol = false;
                }
            }

            if (completeCol) {
                colAdditionalPoints += 5;
            }
        }

        points += colAdditionalPoints;

        return points;
    }

    // From https://github.com/Knall64/someSmallArrayStuff/blob/main/Arrayz.java
    public static int[][] transpose(int[][] a) {
        int[][] re = new int[a[0].length][a.length];

        for(int i=0; i<a.length;i++){
            for(int j=0; j<a[i].length;j++){
                re[j][i] = a[i][j];
            }
        }


        return re;
    }

    public static void main(String[] args) {
        int[][] ar1 = new int[][] {{1, 2, 3}, {4, 5, 6}};
        int[][] ar2 = new int[][] {{1, 0, 2}, {0, 1, 0}};
        System.out.println(pinguballPoints(ar1, ar2));
    }
}
