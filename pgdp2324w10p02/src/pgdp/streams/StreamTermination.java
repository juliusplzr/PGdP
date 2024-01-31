package pgdp.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Optional.empty;

public class StreamTermination {

    // use this array to test your code
    private static final Fish[] fishArray1 = {
            new Fish(Fish.FishSpecies.TROUT, 2.4674354522672535),
            new Fish(Fish.FishSpecies.TROUT, 2.3987024622015913),
            new Fish(Fish.FishSpecies.BASS, 1.6221011392516258),
            new Fish(Fish.FishSpecies.TUNA, 0.6551174751440525),
            new Fish(Fish.FishSpecies.BASS, 2.3729264240076686),
            new Fish(Fish.FishSpecies.TROUT, 1.4823373020017798),
            new Fish(Fish.FishSpecies.BASS, 3.0724152398031173),
            new Fish(Fish.FishSpecies.SALMON, 1.302678822999129),
            new Fish(Fish.FishSpecies.BASS, 1.735278747377438),
            new Fish(Fish.FishSpecies.TUNA, 1.9131959788168622),
            new Fish(Fish.FishSpecies.SALMON, 2.0158386794795854),
            new Fish(Fish.FishSpecies.SALMON, 2.515328160033575),
            new Fish(Fish.FishSpecies.TROUT, 2.2230861878057824),
            new Fish(Fish.FishSpecies.TROUT, 0.5752022006151766),
            new Fish(Fish.FishSpecies.TROUT, 2.3893437639831623),
            new Fish(Fish.FishSpecies.TUNA, 1.6772914967282384),
            new Fish(Fish.FishSpecies.SALMON, 2.1153675280682207),
            new Fish(Fish.FishSpecies.TUNA, 1.3137720639901622),
            new Fish(Fish.FishSpecies.SALMON, 2.3144682920607567),
            new Fish(Fish.FishSpecies.TUNA, 1.0865502493320984)
    };

    public static void main(String[] args) {
        consumeTrouts(Arrays.stream(fishArray1));
        // you can add tests for the other methods here
    }

    // TODO 4.1
    public static List<Fish> sellFishList(Stream<Fish> stream) {
        return stream.toList();
    }

    // TODO 4.2
    public static Fish[] sellFishArray(Stream<Fish> stream) {
        return stream.toArray(Fish[]::new);
    }

    // TODO 4.3
    public static long numberOfDifferentSpecies(Stream<Fish> stream) {
        return stream.map(Fish::getSpecies).distinct().count();
    }

    // TODO 4.4
    public static void consumeTrouts(Stream<Fish> stream) {
        stream.filter(fish -> fish.getSpecies() == Fish.FishSpecies.TROUT).
                forEach(fish -> {double w = fish.getWeight();
                    System.out.println("Sofia fand TROUT mit Gewicht " + w + "kg sehr lecker!");})
        ;
    }

    // TODO 4.5
    public static Optional<Fish> heaviestFish(Stream<Fish> stream) {
        return stream.max(Comparator.comparingDouble(Fish::getWeight));
    }

    // TODO 4.6
    public static boolean hasHeavyTuna(Stream<Fish> stream) {
        return stream.anyMatch(fish -> fish.getSpecies() == Fish.FishSpecies.TUNA && fish.getWeight() >= 2.0);
    }

    // TODO 4.7
    public static double totalFishWeight(Stream<Fish> stream) {
        return stream.mapToDouble(Fish::getWeight).sum();
    }

}
