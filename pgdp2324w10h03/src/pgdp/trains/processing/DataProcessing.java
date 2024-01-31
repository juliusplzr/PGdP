package pgdp.trains.processing;

import pgdp.trains.connections.Station;
import pgdp.trains.connections.TrainConnection;
import pgdp.trains.connections.TrainStop;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataProcessing {

    public static Stream<TrainConnection> cleanDataset(Stream<TrainConnection> connections) {
        return connections.distinct()
                .sorted(Comparator.comparing(ts -> ts.getFirstStop().scheduled()))
                .map(c -> c.withUpdatedStops(c.stops().stream()
                        .filter(s -> s.kind() != TrainStop.Kind.CANCELLED).toList()));
    }

    public static TrainConnection worstDelayedTrain(Stream<TrainConnection> connections) {
        return connections.max(Comparator.comparingInt(o ->
                o.stops().stream().mapToInt(TrainStop::getDelay).max().orElse(0))).orElse(null);
    }

    public static double percentOfKindStops(Stream<TrainConnection> connections, TrainStop.Kind kind) {
        return connections.flatMap(tr -> tr.stops().stream())
                .mapToInt(ts -> {
                    if(ts.kind() == kind) {
                        return 1;
                    } else {
                        return 0;
                    }
                }).average().orElse(0.0) * 100;
    }

    public static double averageDelayAt(Stream<TrainConnection> connections, Station station) {
        return connections.flatMap(tr -> tr.stops().stream()).filter(ts -> ts.station().equals(station))
                .mapToInt(TrainStop::getDelay).average().orElse(0.0);
    }

    public static Map<String, Double> delayComparedToTotalTravelTimeByTransport(Stream<TrainConnection> connections) {
        Map<String, List<TrainConnection>> typeToConnections = connections
                .collect(Collectors.groupingBy(TrainConnection::type));

        return typeToConnections.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        lt -> {
                            double totalScheduled = lt.getValue().stream()
                                    .mapToInt(TrainConnection::totalTimeTraveledScheduled).sum();
                            double totalActual = lt.getValue().stream()
                                    .mapToInt(tc -> Math.max(tc.totalTimeTraveledActual(), tc.totalTimeTraveledScheduled())).sum();
                            return ((totalActual - totalScheduled)) / totalActual * 100;
                        })
                );
    }

    public static Map<Integer, Double> averageDelayByHour(Stream<TrainConnection> connections) {
        Map<Integer, List<TrainStop>> hourToTrainStops = connections
                .flatMap(tc -> tc.stops().stream())
                .collect(Collectors.groupingBy(stop -> stop.actual().getHour()));

        return hourToTrainStops.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream().mapToInt(TrainStop::getDelay).average().orElse(0.0)
        ));
    }

    public static void main(String[] args) {
        // Um alle Verbindungen aus einer Datei zu lesen, verwendet DataAccess.loadFile("path/to/file.json"), etwa:
        // Stream<TrainConnection> trainConnections = DataAccess.loadFile("connections_test/fullDay.json");

        // Oder alternativ über die API, dies aber bitte sparsam verwenden, um die API nicht zu überlasten.
        //Stream<TrainConnection> trainsMunich = DataAccess.getDepartureBoardNowFor(Station.MUENCHEN_HBF);

        List<TrainConnection> trainConnections = List.of(
                new TrainConnection("ICE 2", "ICE", "2", "DB", List.of(
                        new TrainStop(Station.MUENCHEN_HBF,
                                LocalDateTime.of(2022, 12, 1, 11, 0),
                                LocalDateTime.of(2022, 12, 1, 11, 0),
                                TrainStop.Kind.REGULAR),
                        new TrainStop(Station.NUERNBERG_HBF,
                                LocalDateTime.of(2022, 12, 1, 11, 30),
                                LocalDateTime.of(2022, 12, 1, 12, 0),
                                TrainStop.Kind.REGULAR)
                )),
                new TrainConnection("ICE 1", "ICE", "1", "DB", List.of(
                        new TrainStop(Station.MUENCHEN_HBF,
                                LocalDateTime.of(2022, 12, 1, 10, 0),
                                LocalDateTime.of(2022, 12, 1, 10, 0),
                                TrainStop.Kind.REGULAR),
                        new TrainStop(Station.NUERNBERG_HBF,
                                LocalDateTime.of(2022, 12, 1, 10, 30),
                                LocalDateTime.of(2022, 12, 1, 10, 30),
                                TrainStop.Kind.REGULAR)
                )),
                new TrainConnection("ICE 3", "ICE", "3", "DB", List.of(
                        new TrainStop(Station.MUENCHEN_HBF,
                                LocalDateTime.of(2022, 12, 1, 12, 0),
                                LocalDateTime.of(2022, 12, 1, 12, 0),
                                TrainStop.Kind.REGULAR),
                        new TrainStop(Station.AUGSBURG_HBF,
                                LocalDateTime.of(2022, 12, 1, 12, 20),
                                LocalDateTime.of(2022, 12, 1, 13, 0),
                                TrainStop.Kind.CANCELLED),
                        new TrainStop(Station.NUERNBERG_HBF,
                                LocalDateTime.of(2022, 12, 1, 13, 30),
                                LocalDateTime.of(2022, 12, 1, 13, 30),
                                TrainStop.Kind.REGULAR)
                ))
        );

        List<TrainConnection> cleanDataset = cleanDataset(trainConnections.stream()).toList();
        // cleanDataset sollte sortiert sein: [ICE 1, ICE 2, ICE 3] und bei ICE 3 sollte der Stopp in AUGSBURG_HBF
        // nicht mehr enthalten sein.

        TrainConnection worstDelayedTrain = worstDelayedTrain(trainConnections.stream());
        // worstDelayedTrain sollte ICE 3 sein. (Da der Stop in AUGSBURG_HBF mit 40 Minuten Verspätung am spätesten ist.)

        double percentOfKindStops = percentOfKindStops(trainConnections.stream(), TrainStop.Kind.CANCELLED);
        // percentOfKindStops REGULAR sollte 85.71428571428571 sein, CANCELLED 14.285714285714285.

        double averageDelayAt = averageDelayAt(trainConnections.stream(), Station.NUERNBERG_HBF);
        // averageDelayAt sollte 10.0 sein. (Da dreimal angefahren und einmal 30 Minuten Verspätung).

        Map<String, Double> delayCompared = delayComparedToTotalTravelTimeByTransport(trainConnections.stream());
        // delayCompared sollte ein Map sein, die für ICE den Wert 16.666666666666668 hat.
        // Da ICE 2 0:30 geplant hatte, aber 1:00 gebraucht hat, ICE 1 0:30 geplant und gebraucht hatte, und
        // ICE 3 1:30 geplant und gebraucht hat. Zusammen also 2:30 geplant und 3:00 gebraucht, und damit
        // (3:00 - 2:30) / 3:00 = 16.666666666666668.

        Map<Integer, Double> averageDelayByHourOfDay = averageDelayByHour(trainConnections.stream());
        // averageDelayByHourOfDay sollte ein Map sein, die für 10, 11 den Wert 0.0 hat, für 12 den Wert 15.0 und
        // für 13 den Wert 20.0.

    }

}
