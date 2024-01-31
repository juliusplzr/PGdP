package pgdp.threads;

public class CorrectionScheme {
	private static int[][] points = new int[][] { { 0, 6, 0, 4 },
			{ 0, 2, 5, 0 }, { 6, 0, 0, 2 }, { 2, 0, 0, 0 }, { 0, 4, 3, 0 },
			{ 3, 0, 5, 7 }, { 3, 0, 0, 6 }, { 1, 0, 4, 0 } };

	public static int points(int index, char answer) {
		if (index <= 0 || index > 8)
			throw new IllegalArgumentException(
					"Index muss im Bereich [1,8] sein, aber war " + index);
		index--;
		return points[index][answer - 'a'];
	}

	public static float grade(int points) {
		if (points >= 38)
			return 1.0f;
		else if (points > 36)
			return 1.3f;
		else if (points > 33)
			return 1.7f;
		else if (points > 30)
			return 2.0f;
		else if (points > 27)
			return 2.3f;
		else if (points > 24)
			return 2.7f;
		else if (points > 22)
			return 3.0f;
		else if (points > 20)
			return 3.3f;
		else if (points > 18)
			return 3.7f;
		else if (points > 16)
			return 4.0f;
		else if (points > 12)
			return 4.3f;
		else if (points > 8)
			return 4.7f;
		return 5.0f;
	}
}