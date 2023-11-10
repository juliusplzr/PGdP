package pgdp.array;

public class Switch {

	public static String locationOfLectureHall(String hall) {
		String s = "";

		switch (hall) {
			case "MI HS 2", "Interims I 1" -> s = "Informatik";
			case "MW0001", "MW2001" -> s = "Maschinenwesen";
			case "Interims II 2" -> s = "Chemie";
			case "Carl-von-Linde", "N1190" -> s = "Innenstadt";
            default -> s = "Unbekannter Hörsaal";
		}

		return s;
	}

	public static int inclusions(char c) {
		int inclusions;

		switch (c) {
			case 'C', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
					'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'c', 'f', 'h', 'i', 'j',
					'k', 'l', 'm', 'n', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1',
					'2', '3', '5', '7' ->
				inclusions = 0;
			case 'A', 'D', 'O', 'P', 'Q', 'R', 'a', 'b', 'd', 'e', 'o', 'p',
					'q', '4', '6', '9' -> inclusions = 1;
			case 'B', '8', 'g', '0' -> inclusions = 2;
			default -> inclusions = -1;
		}

		return inclusions;
	}

	public static String formatDate(int day, int month, int weekday) {
		String s;

		switch (weekday) {
			case 1 -> s = "Montag";
			case 2 -> s = "Dienstag";
			case 3 -> s = "Mittwoch";
			case 4 -> s = "Donnerstag";
			case 5 -> s = "Freitag";
			case 6 -> s = "Samstag";
			case 7 -> s = "Sonntag";
			default -> {
				s = "Undefiniertes Datum";
				return s;
			}
		}

		s += (", den " + day + ". ");

		switch (month) {
			case 1 -> s += "Januar";
			case 2 -> s += "Februar";
			case 3 -> s += "März";
			case 4 -> s += "April";
			case 5 -> s += "Mai";
			case 6 -> s += "Juni";
			case 7 -> s += "Juli";
			case 8 -> s += "August";
			case 9 -> s += "September";
			case 10 -> s += "Oktober";
			case 11 -> s += "November";
			case 12 -> s += "Dezember";
			default -> {
				s = "Undefiniertes Datum";
				return s;
			}
		}

		return s;
	}

	public static int daysInFebruary(int year) {
		int days;
		boolean isLeapYear = (year % 4 == 0);
		boolean exception = (year % 100 == 0 && year % 400 != 0);

		String isLeapYearString = String.valueOf(isLeapYear);
		String exceptionString = String.valueOf(exception);

		switch (isLeapYearString) {
			case "true" -> {
				switch (exceptionString) {
					case "true" -> days = 28;
					default -> days = 29;
				}
			}
			default -> days = 28;
		}

		return days;
	}

	public static int daysLeftInYearAfter(int day, int month, int year) {
		int daysPassed;

		switch (month) {
			case 1 -> daysPassed = 0;
			case 2 -> daysPassed = 31;
			case 3 -> daysPassed = 31 + daysInFebruary(year);
			case 4 -> daysPassed = 62 + daysInFebruary(year);
			case 5 -> daysPassed = 92 + daysInFebruary(year);
			case 6 -> daysPassed = 123 + daysInFebruary(year);
			case 7 -> daysPassed = 153 + daysInFebruary(year);
			case 8 -> daysPassed = 184 + daysInFebruary(year);
			case 9 -> daysPassed = 215 + daysInFebruary(year);
			case 10 -> daysPassed = 245 + daysInFebruary(year);
			case 11 -> daysPassed = 276 + daysInFebruary(year);
			case 12 -> daysPassed = 306 + daysInFebruary(year);
			default -> {
				return -1;
			}
		}

		daysPassed += day;

		boolean yearMulOf4 = year % 4 == 0;
		boolean exception = (year % 100 == 0 && year % 400 != 0);
		String yearMulOf4String = String.valueOf(yearMulOf4);
		String exceptionString = String.valueOf(exception);

		int daysTotal;
		boolean isleapYear;

		switch (yearMulOf4String) {
			case "true" -> {
				switch (exceptionString) {
					case "true" -> isleapYear = false;
					default -> isleapYear = true;
				}
			}
			default -> isleapYear = false;
		}

		String isLeapYearString = String.valueOf(isleapYear);

		switch (isLeapYearString) {
			case "true" -> daysTotal = 366;
			default -> daysTotal = 365;
		}
		return (daysTotal - daysPassed);
	}
}
