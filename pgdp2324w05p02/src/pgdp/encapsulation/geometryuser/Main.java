package pgdp.encapsulation.geometryuser;

import pgdp.encapsulation.geometry.Point;
import pgdp.encapsulation.geometry.Rectangle;

public class Main {

    public static void main(String... args) {
        Rectangle rectangle = new Rectangle(new Point(1, 1), 3, 2);

        System.out.println("Just created the following rectangle:");
        System.out.println("Höhe: " + rectangle.height);
        System.out.println("Breite: " + rectangle.width);
        System.out.println("Fläche: " + rectangle.height * rectangle.width);
    }

}
