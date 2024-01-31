package pgdp.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class StreamModification {
    public static int seed = (new Random()).nextInt();

    public static void main(String[] args) {
        Stream<Fish> exampleFishStream = Arrays.stream(new Fish[]{
                new Fish(Fish.FishSpecies.TROUT, 1.2),
                new Fish(Fish.FishSpecies.BASS, 1.34),
                new Fish(Fish.FishSpecies.SALMON, 0.69),
                new Fish(Fish.FishSpecies.SALMON, 0.123),
                new Fish(Fish.FishSpecies.TROUT, 2.44),
                new Fish(Fish.FishSpecies.TUNA, 4.20),
        });

        limitIncomingFish(exampleFishStream, 5).forEach(System.out::println);
        /*
            expected output:
            Blub blub, I am a TROUT and weigh 1.2 kg!
            Blub blub, I am a BASS and weigh 1.34 kg!
         */

        // you can add tests for the other methods here
    }

    // TODO 3.1
    public static Stream<Fish> limitIncomingFish(Stream<Fish> fishStream, int limit) {
        return fishStream.limit(limit).takeWhile(fish -> fish.getWeight() >= 0.8);
    }

    // TODO 3.2
    public static Stream<Fish> filterSpecies(Stream<Fish> fishStream, Fish.FishSpecies species) {
        return fishStream.filter(fish -> fish.getSpecies() == species);
    }

    // TODO 3.3
    public static Stream<Fish> printAllFish(Stream<Fish> fishStream) {
        return fishStream.peek(System.out::println);
    }

    // TODO 3.4
    public static void printAvailableSpecies(Stream<Fish> fishStream) {
        fishStream.map(Fish::getSpecies).distinct().forEach(System.out::println);
    }

    // TODO 3.5
    public static Stream<Fish> getBabyFish(Stream<Fish> fishStream) {
        return fishStream.flatMap(fish -> Stream.concat(Stream.of(fish), Arrays.stream(birthHelper(fish))));
    }

    // TODO 3.6
    public static Stream<Fish> sortByWeight(Stream<Fish> stream) {
        return stream.sorted(Comparator.comparingDouble(Fish::getWeight));
    }

    /*
        Complete method - do not change!
        Returns a random number of baby fish (between 1 and 5).
     */
    private static Fish[] birthHelper(Fish mother) {
        Random random = new Random(seed);
        Fish[] babies = new Fish[random.nextInt(4) + 1];

        for (int i = 0; i < babies.length; i++) {
            double babyWeight = random.nextDouble() * 2;
            babies[i] = new Fish(mother.getSpecies(), babyWeight);
        }
        return babies;
    }
}
