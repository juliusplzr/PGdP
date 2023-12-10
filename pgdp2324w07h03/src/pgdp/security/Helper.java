package pgdp.security;

public class Helper {

	/**
	 * Diese Methode kann dir die Strings einfärben, damit debuggen u.U leichter
	 * wird. Die Tests akzeptieren beide Varianten, also normal und gefärbt.
	 * 
	 * @param depiction der String, der gefärbt werden soll
	 * @return der gefärbte String
	 */
	public static String changeColors(String depiction) {
		StringBuilder sb = new StringBuilder();
		for (String s : depiction.split("/")) {
			sb.append(colorString(s) + "/");
		}
		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	private static String colorString(String depiction) {
		return switch (depiction) {
		case "green" -> "\u001B[32m" + depiction;
		case "blue" -> "\u001B[94m" + depiction;
		case "yellow" -> "\u001B[33m" + depiction;
		case "doubleYellow" -> "\u001B[33m" + depiction;
		case "[SC]" -> "\u001B[33m[\u001B[0mSC\u001B[33m]";
		case "red" -> "\u001B[31m" + depiction;
		case "chequered" -> "\u001B[40m\u001B[97m" + "c" + "\u001B[107m\u001B[30m" + "h" + "\u001B[40m\u001B[97m" + "e"
				+ "\u001B[107m\u001B[30m" + "q" + "\u001B[40m\u001B[97m" + "u" + "\u001B[107m\u001B[30m" + "e"
				+ "\u001B[40m\u001B[97m" + "r" + "\u001B[107m\u001B[30m" + "e" + "\u001B[40m\u001B[97m" + "d";
		default -> depiction;
		} + "\u001B[0m";
	}
}