package pgdp.trains;

public class Carriage {
    private boolean isRestaurantCarriage;
    private int emptyWeight;
    private Person[] passengers;

    public Carriage(boolean isRestaurantCarriage, int emptyWeight) {
        this.isRestaurantCarriage = isRestaurantCarriage;
        this.emptyWeight = emptyWeight;
        this.passengers = new Person[] {};
    }

    public boolean isRestaurantCarriage() {
        return this.isRestaurantCarriage;
    }

    public int getEmptyWeight() {
        return emptyWeight;
    }

    public Person[] getPassengers() {
        return passengers;
    }

    public void boardPeople(Person[] passengers) {
        Person[] nPassengers = new Person[passengers.length + this.passengers.length];

        for (int i = 0; i < this.passengers.length; i++) {
            nPassengers[i] = this.passengers[i];
        }

        for (int j = 0; j < passengers.length; j++) {
            nPassengers[j + this.passengers.length] = passengers[j];
        }

        this.passengers = nPassengers;
    }
}
