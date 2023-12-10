package pgdp.security;

public class Main {

	/**
	 * Eine kleine main zum Testen. Das Ergebnis soll wie auf Artemis ausschauen.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Track track = new Track(10);
		track.printStatus();
		track.createLappedCarAt(2);
		track.printStatus();

		track.removeLappedCarAt(2);
		track.printStatus();

		track.createHazardAt(3, 6);
		track.printStatus();

		track.removeHazardAt(3, 6);
		track.printStatus();

		track.setAll("[SC]", true);
		track.printStatus();

		track.setAll("clear", false);
		track.printStatus();

		track.setAll("end", true);
		track.printStatus();
	}
}
