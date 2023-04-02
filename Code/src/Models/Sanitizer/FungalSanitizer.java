package Models.Sanitizer;

import java.util.Set;

public class FungalSanitizer extends Sanitizer{
    private final static int totalFungi = 15 * (10 ^ 5);

    public FungalSanitizer(int noKilledOrganisms, Set<String> ingredients, Set<String> surfaces) {
        super(noKilledOrganisms, ingredients, surfaces);
        this.efficiency = (double) this.noKilledOrganisms / totalFungi;
        this.price = this.findPrice();
    }
    public String toString() {
        return "FungalSanitizer{" +
                "noKilledOrganisms=" + noKilledOrganisms +
                ", ingredients=" + ingredients +
                ", surfaces=" + surfaces +
                ", price=" + price +
                ", efficiency=" + efficiency +
                '}';
    }
}
