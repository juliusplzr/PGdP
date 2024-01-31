package pgdp.streamwarmup;

public class Fish {
    // This class is complete - do not change!
    private final FishSpecies species;
    private final double weight;

    public Fish(FishSpecies species, double weight) {
        this.species = species;
        this.weight = weight;
    }

    public FishSpecies getSpecies() {
        return species;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Blub blub, I am a " + species + " and weigh " + weight + " kg!";
    }

    public enum FishSpecies {
        TROUT,
        SALMON,
        TUNA,
        BASS
    }
}
