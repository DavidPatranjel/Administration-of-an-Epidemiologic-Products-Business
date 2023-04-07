package Models.Sanitizer;

import java.util.Set;

public class BacteriaSanitizer extends Sanitizer{
    private final static double totalBacteria = 1e9;

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
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BacteriaSanitizer)) {
            return false;
        }

        BacteriaSanitizer other = (BacteriaSanitizer) obj;
        return super.equals(other);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}