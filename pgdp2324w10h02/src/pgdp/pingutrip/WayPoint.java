package pgdp.pingutrip;

public record WayPoint(double x, double y) {

    /**
     * @param string - a line of the form "x;y" where x and y are doubles.
     * @return a WayPoint with the x,y coordinates.
     */
    public static WayPoint ofString(String string) {
        String[] split = string.split("([;:])");
        double x = Double.parseDouble(split[0].trim());
        double y = Double.parseDouble(split[1].trim());
        return new WayPoint(x, y);
    }

    public double distanceTo(WayPoint point) {
        return Math.sqrt(Math.pow(x - point.x, 2) + Math.pow(y - point.y, 2));
    }

}
