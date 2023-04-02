package Models.Sanitizer;

import java.util.Set;

public class VirusSanitizer extends Sanitizer{
    private final static int totalVirus = 10 ^ 8;

    public VirusSanitizer(int noKilledOrganisms, Set<String> ingredients, Set<String> surfaces) {
        super(noKilledOrganisms, ingredients, surfaces);
        this.efficiency = (double) this.noKilledOrganisms / totalVirus;
        this.price = this.findPrice();
    }
    public String toString() {
        return "VirusSanitizer{" +
                "noKilledOrganisms=" + noKilledOrganisms +
                ", ingredients=" + ingredients +
                ", surfaces=" + surfaces +
                ", price=" + price +
                ", efficiency=" + efficiency +
                '}';
    }
}

