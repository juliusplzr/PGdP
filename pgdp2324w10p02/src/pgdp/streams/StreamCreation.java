package pgdp.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class StreamCreation {

    public static void main(String[] args) {
        Fish fish1 = new Fish(Fish.FishSpecies.TROUT, 1.2);
        Fish fish2 = new Fish(Fish.FishSpecies.BASS, 1.5);
        Fish fish3 = new Fish(Fish.FishSpecies.TUNA, 0.9);

        analyze(caughtWithHand(fish1, fish2, fish3));
        // you can add tests for the other methods here
    }

    // TODO 2.1
    public static Stream<Fish> caughtWithHand(Fish one, Fish two, Fish three) {
        return Stream.of(one, two, three);
    }

    // TODO 2.2
    public static Stream<Fish> caughtWithFishingRod(Fish[] caughtFish) {
        return Arrays.stream(caughtFish);
    }

    // TODO 2.3
    public static Stream<Fish> caughtWithFishNet(Collection<Fish> caughtFish) {
        return caughtFish.stream();
    }

    // TODO 2.4
    public static Stream<Fish> caughtInFishFarm(Supplier<Fish> fishFarm) {
        return Stream.generate(fishFarm);
    }

    //This method is complete - do not change!
    public static void analyze(Stream<Fish> fishDelivery) {
        fishDelivery.forEach(System.out::println);
    }
}