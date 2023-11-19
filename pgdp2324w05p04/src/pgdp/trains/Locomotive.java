package pgdp.trains;

public class Locomotive {
    private Person driver;
    private int emptyWeight;

    public Locomotive(int emptyWeight, Person driver) {
        this.emptyWeight = emptyWeight;
        this.driver = driver;
    }

    public int getEmptyWeight() {
        return emptyWeight;
    }

    public Person getDriver() {
        return driver;
    }

    public void setDriver(Person driver) {
        this.driver = driver;
    }
}
