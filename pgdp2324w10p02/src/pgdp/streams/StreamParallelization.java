package pgdp.streams;

import java.util.Random;
import java.util.stream.Stream;

public class StreamParallelization {
    public static void main(String[] args) {
        System.out.println(calculationOfWeights(takeFishFromFishFarm(), 5_000_000));
    }

    //TODO 5.4
    public static double calculationOfWeights(Stream<Fish> fishStream, int limit) {
        return fishStream.limit(limit)
                .parallel()
                .mapToDouble(Fish::getWeight)
                .map(weight -> Math.pow(Math.pow(weight, 4) + 1.5, 0.7) * 3)
                .sum();
    }

    // This method is complete - do not change!
    public static Stream<Fish> takeFishFromFishFarm() {
        return Stream.generate(() -> {
            Random random = new Random(22);
            Fish.FishSpecies[] species = Fish.FishSpecies.values();
            return new Fish(
                    species[random.nextInt() % species.length],
                    random.nextDouble(1.0, 2.0)
            );
        });
    }
}
