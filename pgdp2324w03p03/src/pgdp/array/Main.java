package pgdp.array;

import static pgdp.array.Switch.*;

public class Main {

	public static void main(String[] args) {
		int day = 1;
		int month = 1;
		int year = 2100;
		System.out.println(daysLeftInYearAfter(day, month, year));
	}
}