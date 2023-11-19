package pgdp.datatypes.time;

public class Appointment {
    Date date;
    TimeOfDay start;
    TimeOfDay end;
    String description;

    public Appointment(Date date, TimeOfDay start, TimeOfDay end, String description) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    public String toString() {
        String output = "";
        output += description + "\n";
        output += date.toString() + "\n";
        output += start.toString() + "\n";
        output += start.hoursUntil(end) + " h";
        return output;
    }
}
