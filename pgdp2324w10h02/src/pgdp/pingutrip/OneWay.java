package pgdp.pingutrip;

public record OneWay(WayPoint from, WayPoint to) {

    public static final int DEGREES_360 = 360;
    public static final int DEGREES_180 = 180;
    public static final double STEP_SIZE = 0.7;
    public static final double TOLERANCE = 0.001;

    /**
     * @return the distance between the two waypoints.
     */
    public double getLength() {
        return from().distanceTo(to);
    }

    /**
     * @return the direction of the way in degrees in the range [0, 360)
     * with 0 is north, 90 is east, 180 is south, 270 is west.
     */
    public int getDirection() {
        return (int) ((Math.atan2(to.y() - from.y(), to.x() - from.x())
                * (DEGREES_180 / Math.PI) + DEGREES_360) % DEGREES_360);
    }

    /**
     * @return a pretty readable string representation of the way.
     */
    public String prettyPrint() {
        return Math.round(getLength() / STEP_SIZE) + " Schritte Richtung " + getDirection() + " Grad.";
    }

    /**
     * @param visit - the waypoint to check if it is on the way.
     * @return true if the waypoint is on the way, false otherwise, with some small leeway.
     */
    public boolean isOnPath(WayPoint visit) {
        double fromToPoint = from.distanceTo(visit);
        double toToPoint = to.distanceTo(visit);
        return Math.abs(fromToPoint + toToPoint - getLength()) < TOLERANCE;
    }
}
