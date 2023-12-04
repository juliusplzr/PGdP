package pgdp.oopwarmup.ourcode;

import pgdp.oopwarmup.foreigncode.Person;
import pgdp.oopwarmup.pingutils.Serializable;

import java.lang.String;

public class SerializablePerson extends Person implements Serializable {
    private String name;
    private int age;

    public SerializablePerson(String name, int age) {
        super(name, age);
    }


    public String serialize() {
        return "SerializablePerson, " + super.getName() + ", " + super.getAge();
    }

    @Override
    public boolean equals(Serializable other) {
        if (other instanceof SerializablePerson) {
            String otherSerialized = other.serialize();

            String otherName = otherSerialized.substring(20, otherSerialized.lastIndexOf(','));
            String otherAge = otherSerialized.substring(otherSerialized.lastIndexOf(',') + 2);

            int otherAgeInt = Integer.parseInt(otherAge);

            String thisName = super.getName();
            int thisAge = super.getAge();

            return otherName.equals(thisName) && thisAge == otherAgeInt;
        }

        return false;
    }

    public static void main(String[] args) {
        SerializablePerson sp = new SerializablePerson("Harry Surname", 20);
        SerializablePerson sp1 = new SerializablePerson("Harry Surname", 19);
        System.out.println(sp.equals(sp1));
        System.out.println(sp1.serialize());
    }
}
