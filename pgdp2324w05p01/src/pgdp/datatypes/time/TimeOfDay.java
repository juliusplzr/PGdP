package pgdp.datatypes.time;

public class TimeOfDay {
    int hour;
    int minute;
    int second;

    public TimeOfDay(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public double hoursUntil(TimeOfDay other) {
        double minFraction = (double) (other.minute - this.minute) / 60;
        double secFraction = (double) (other.second - this.second) / 3600;
        return other.hour - this.hour + minFraction + secFraction;
    }

    public String toString() {
        return this.hour + ":" + this.minute + ":" + this.second;
    }

    public String toStringTwelveHourClock() {
        if (this.hour >= 12) {
            if (this.hour != 12) {
                this.hour -= 12;
            }
            return toString() + " pm";
        } else {
            if (this.hour == 0) {
                this.hour = 12;
            }
            return toString() + " am";
        }
    }

    public static void main(String[] args) {
        TimeOfDay d = new TimeOfDay(10, 10, 10);
        System.out.println(d.toStringTwelveHourClock());
    }
}