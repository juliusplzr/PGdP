package pgdp.datatypes.time;

public class Date {
    int day;
    int month;
    int year;

    public Date(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            System.out.println("Not a valid date. Do NOT use this object!");
        } else {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

    public String toString() {
        return this.day + "." + this.month + "." + this.year;
    }

    public static boolean isValidDate(int day, int month, int year) {
        return month >= 1 && month <= 12
                && day >= 1 && day <= daysOfMonth(month, year);
    }

    // <====================== Helper =====================>
    public static int daysOfMonth(int month, int year) {
        return switch(month) {
            case 4, 6, 9, 11 -> 30;
            case 1, 3, 5, 7, 8, 10, 12 -> 31;
            case 2 -> daysInFebruary(year);
            default -> -1;
        };
    }

    public static int daysInFebruary(int year) {
        int days;
        if(year % 4 == 0 && (!(year % 100 == 0) || year % 400 == 0)) {
            days = 29;
        } else {
            days = 28;
        }
        return days;
    }
}
