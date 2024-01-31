package pgdp.pingutrip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.nio.file.*;

final public class PinguTrip {

    // To hide constructor in utility class.
    private PinguTrip() {}

    public static Stream<WayPoint> readWayPoints(String pathToWayPoints) {
        try {
            return Files.lines(Path.of(pathToWayPoints)).
                    takeWhile(line -> !line.equals("---")).
                    filter(line -> !line.startsWith("//")).
                    map(WayPoint::ofString);
        } catch (IOException e) {
            return Stream.empty();
        }
    }

    public static Stream<OneWay> transformToWays(List<WayPoint> wayPoints) {
        return IntStream.range(0, wayPoints.size() - 1).
                mapToObj(idx -> new OneWay(wayPoints.get(idx) , wayPoints.get(idx + 1)));
    }

    public static double pathLength(Stream<OneWay> oneWays) {
        return oneWays.mapToDouble(OneWay::getLength).sum();
    }

    public static List<OneWay> kidFriendlyTrip(List<OneWay> oneWays) {
        double average = oneWays.stream().mapToDouble(OneWay::getLength).average().orElse(0.0);
        return oneWays.stream().takeWhile(oneWay -> oneWay.getLength() <= average).toList();
    }

    public static WayPoint furthestAwayFromHome(Stream<WayPoint> wayPoints, WayPoint home) {
        return wayPoints.max(Comparator.comparingDouble(wayOne -> wayOne.distanceTo(home))).orElse(home);
    }

    public static boolean onTheWay(Stream<OneWay> oneWays, WayPoint visit) {
        return oneWays.anyMatch(oneWay -> oneWay.isOnPath(visit));
    }

    public static String prettyDirections(Stream<OneWay> oneWays) {
        return oneWays.map(OneWay::prettyPrint).collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        List<WayPoint> wayPoints = readWayPoints("test_paths/path.txt").toList();
        // List.of(new WayPoint(4.0, 11.5), new WayPoint(19.1, 3.2));

        List<OneWay> oneWays = transformToWays(wayPoints).toList();
        // List.of(new OneWay(new WayPoint(4.0, 11.5), new WayPoint(19.1, 3.2)));

        double length = pathLength(oneWays.stream());
        System.out.println(length);
        // 17.230 ...

        List<OneWay> kidFriendly = kidFriendlyTrip(oneWays);
        System.out.println(kidFriendly);
        // List.of(new OneWay(new WayPoint(4.0, 11.5), new WayPoint(19.1, 3.2)));

        WayPoint furthest = furthestAwayFromHome(wayPoints.stream(), wayPoints.get(0));
        System.out.println(furthest);
        // new WayPoint(19.1, 3.2);

        boolean onTheWay = onTheWay(oneWays.stream(), new WayPoint(0.0, 0.0));
        System.out.println(onTheWay);
        // false

        onTheWay = onTheWay(oneWays.stream(), new WayPoint(19.1, 3.2));
        System.out.println(onTheWay);
        // true

        String directions = prettyDirections(oneWays.stream());
        System.out.println(directions);
        // "25 Schritte Richtung 331 Grad."
    }

}
