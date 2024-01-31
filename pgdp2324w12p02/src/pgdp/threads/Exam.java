package pgdp.threads;

public class Exam {
	private final String name;

	private char[] answers;
	private int[] points;
	private int totalPoints = 0;
	private float grade = 5.0f;

	public char getAnswer(int index) {
		if (index <= 0 || index > 8)
			throw new IllegalArgumentException(
					"Index muss im Bereich [1,8] sein, aber war " + index);
		index = index - 1;
		return answers[index];
	}

	public void updateCorrection(int index, int delta) {
		if (index <= 0 || index > 8)
			throw new IllegalArgumentException(
					"Index muss im Bereich [1,8] sein, aber war " + index);
		index--;
		points[index] += delta;
	}

	public int[] getPoints() {
		return points;
	}

	public void setPoints(int index, int points) {
		if (index <= 0 || index > 8)
			throw new IllegalArgumentException(
					"Index muss im Bereich [1,8] sein, aber war " + index);
		index--;
		this.points[index] = points;
	}

	public String getName() {
		return name;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public Exam() {
		this.name = lastNames[rand.nextInt(lastNames.length)] + ", "
				+ firstNames[rand.nextInt(firstNames.length)];

		answers = new char[8];
		points = new int[answers.length];
		for (int i = 0; i < answers.length; i++) {
			answers[i] = (char) (rand.nextInt(4) + 'a');
			points[i] = -1;
		}
	}

	@Override
	public String toString() {
		return name + " hat " + totalPoints
				+ " von 40 möglichen Punkten und eine " + grade
				+ " geschrieben.";
	}

	private static String[] firstNames = new String[] { "Ean", "Markus",
			"Patrik", "Jonte", "Milan", "Bent", "Rouven", "Aljoscha", "Johnny",
			"Said", "Salim", "Lorenz", "Gerald", "Samuel", "Mailo", "Dirk",
			"Levi", "Liam", "Elias", "Frank", "Burglind", "Isabella", "Leila",
			"Lani", "Samara", "Vera", "Nelli", "Fee", "Gloria", "Renesmee",
			"Rachel", "Ida", "Lina", "Mathilda", "Laura", "Emma", "Emilia",
			"Fatme", "Bele", "Ella", "Julian", "Claudia", "Karl-Heinz", "Indi", "Lea" };
	private static String[] lastNames = new String[] { "Mueller", "Maier",
			"Schmidt", "Schulz", "Kranz", "Schroeder", "Behrens", "Jansen",
			"Hunter", "Ahler" };
	private static java.util.Random rand = new java.util.Random();
}