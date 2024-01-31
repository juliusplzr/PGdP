package pgdp.streamwarmup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BingeStreaming {
    public static void main(String[] args) {
        List<Fish> l = new ArrayList<>();
        l.add(new Fish(Fish.FishSpecies.SALMON, 1));
        l.add(new Fish(Fish.FishSpecies.BASS, 2));
        l.add(new Fish(Fish.FishSpecies.BASS, 1));
        l.add(new Fish(Fish.FishSpecies.TROUT, 3));
        l.add(new Fish(Fish.FishSpecies.TROUT, 1));

        Map<Fish.FishSpecies, Double> map = new HashMap<>();
        map.put(Fish.FishSpecies.SALMON, 1d);
        map.put(Fish.FishSpecies.TROUT, 2d);
        map.put(Fish.FishSpecies.TUNA, 1.5d);
        map.put(Fish.FishSpecies.BASS, 0.5d);

        System.out.println(avgWeightSalmonAndBass(l));
    }

    // TODO 1
    public static int avgWeightSalmonAndBass(List<Fish> fishes){
        return (int) fishes.stream().filter(fish -> fish.getSpecies() == Fish.FishSpecies.SALMON ||
                fish.getSpecies() == Fish.FishSpecies.BASS).mapToDouble(Fish::getWeight).average().orElse(0);
    }

    // TODO 2
    public static double price(List<Fish> boughtFishes, Map<Fish.FishSpecies, Double> priceTable){
        return boughtFishes.stream().filter(fish -> boughtFishes.indexOf(fish) % 10 != 9).
                mapToDouble(fish -> priceTable.get(fish.getSpecies()) * fish.getWeight()).sum();
    }

    // TODO 3
    public static Map<Fish.FishSpecies, List<Double>> weightPerSpecies(List<Fish> fishes){
        Map<Fish.FishSpecies, List<Double>> weights = new HashMap<>();

        fishes.stream().map(Fish::getSpecies).distinct().forEach(fishSpecies -> weights.put(fishSpecies, new ArrayList<>()));

        fishes.stream().forEach(fish -> weights.get(fish.getSpecies()).add(fish.getWeight()));

        return weights;
    }

    // TODO 4
    public static Map<Fish.FishSpecies, Double> avgWeightPerSpecies(List<Fish> fishes){
        Map<Fish.FishSpecies, Double> avgWeights = new HashMap<>();

        weightPerSpecies(fishes).entrySet().stream().forEach(fishSpecies -> avgWeights.put(fishSpecies.getKey(),
                fishSpecies.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0)));

        return avgWeights;
    }
}
