package Models.Sanitizer;

import java.util.Set;

public class BacteriaSanitizer extends Sanitizer{
    private final static int totalBacteria = 10 ^ 9;

    public BacteriaSanitizer(int noKilledOrganisms, Set<String> ingredients, Set<String> surfaces) {
        super(noKilledOrganisms, ingredients, surfaces);
        this.efficiency = (double) this.noKilledOrganisms / totalBacteria;
        this.price = this.findPrice();
    }
    public String toString() {
        return "BacteriaSanitizer{" +
                "noKilledOrganisms=" + noKilledOrganisms +
                ", ingredients=" + ingredients +
                ", surfaces=" + surfaces +
                ", price=" + price +
                ", efficiency=" + efficiency +
                '}';
    }
}