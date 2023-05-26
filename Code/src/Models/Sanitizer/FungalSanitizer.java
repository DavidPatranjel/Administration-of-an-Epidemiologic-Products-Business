package Models.Sanitizer;

import java.util.Set;

public final class FungalSanitizer extends Sanitizer{
    private final static double totalFungi = 15 * (1e5);

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
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FungalSanitizer)) {
            return false;
        }

        FungalSanitizer other = (FungalSanitizer) obj;
        return super.equals(other);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
