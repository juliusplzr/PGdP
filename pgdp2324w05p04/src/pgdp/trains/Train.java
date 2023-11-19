package pgdp.trains;

public class Train {
    private Carriage[] carriages;

    private Locomotive locomotive;
    public Train(Locomotive locomotive) {
        this.locomotive = locomotive;
        this.carriages = new Carriage[] {};
    }

    public Carriage[] getCarriages() {
        return carriages;
    }

    public void setCarriages(Carriage[] carriages) {
        this.carriages = carriages;
    }

    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public Locomotive getLocomotive() {
        return this.locomotive;
    }

    public int getTotalWeight() {
        int totalWeight = 0;

        for (Carriage carriage : carriages) {
            totalWeight += carriage.getEmptyWeight();
            Person[] passengersInCarriage = carriage.getPassengers();

            for (Person person : passengersInCarriage) {
                totalWeight += person.getWeight();
            }
        }

        int locomotiveWeight = this.getLocomotive().getEmptyWeight();

        int driverWeight = this.getLocomotive().getDriver().getWeight();

        return totalWeight + driverWeight + locomotiveWeight;
    }

    public int getNumberOfPassengers() {
       int passengerCount = 0;

        for (Carriage carriage : carriages) {
            Person[] passengersInCarriage = carriage.getPassengers();

            for (Person person : passengersInCarriage) {
                if (person != null) {
                    passengerCount++;
                }
            }

        }

       return passengerCount;
    }

    public void appendCarriage(Carriage carriage) {
        Carriage[] nCarriages = new Carriage[this.carriages.length + 1];

        System.arraycopy(carriages, 0, nCarriages, 0, this.carriages.length);

        nCarriages[this.carriages.length] = carriage;

        this.carriages = nCarriages;
    }

    public static void main(String[] args) {
        Carriage testCarriage1 = new Carriage(false, 50);
        Carriage testCarriage2 = new Carriage(false, 70);
        Carriage testCarriage3 = new Carriage(false, 35);

        Person testPassenger1 = new Person(30, "Weighs as much as a fly");
        Person testPassenger2 = new Person(10, "and this one even less");
        Person testPassenger3 = new Person(10, "classical music is his food");
        Person testDriver = new Person(15, "he basically invisible");

        testCarriage1.boardPeople(new Person[] {testPassenger1});
        testCarriage1.boardPeople(new Person[] {testPassenger2});
        testCarriage3.boardPeople(new Person[] {testPassenger3});

        Locomotive testLocomotive = new Locomotive(100, testDriver);

        Train testTrain = new Train(testLocomotive);
        testTrain.appendCarriage(testCarriage1);
        testTrain.appendCarriage(testCarriage2);
        testTrain.appendCarriage(testCarriage3);

        System.out.println(testTrain.getTotalWeight());
    }

}
