package pgdp.array;

import java.util.Arrays;
import static pgdp.array.Array.*;

public class Main {

	public static void main(String[] args) {
		//char[][] letterGrid = {
		//{'A', 'P', 'A', 'N', 'G', 'U'},
		//{'P', 'P', 'I', 'N', 'G', 'E'},
		//{'E', 'I', 'I', 'N', 'A', 'A'},
		//{'N', 'N', 'N', 'N', 'K', 'A'},
		//{'G', 'G', 'Q', 'Q', 'G', 'U'},
		//{'U', 'O', 'Q', 'Q', 'U', 'U'}
		//};
		//char[] word = new char[] {'P', 'I', 'N', 'G', 'U'};
		//System.out.println(Array.crossword(letterGrid, new char[] {'P', 'I', 'N', 'G', 'U'}));

		int[][] matrix = new int[][] {{1, 2, 3, 4},{5, 6, 7, 8}};

		System.out.println(Arrays.deepToString(transpose(matrix)));
	}
}