package pgdp.array;

import java.util.Arrays;
import static pgdp.array.Array.*;

public class Main {

	public static void main(String[] args) {
		int[] fischbestand = { 1, 2, 2, 1, 0, 0 };
		System.out.println(Arrays.toString(fischbestand));
		System.out.println(Arrays.toString(distinct(fischbestand)));
	}
}

