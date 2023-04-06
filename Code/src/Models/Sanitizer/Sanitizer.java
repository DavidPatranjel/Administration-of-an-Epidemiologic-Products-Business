package Models.Sanitizer;

import java.util.Set;

public abstract class Sanitizer implements Comparable<Sanitizer>{
    protected int noKilledOrganisms;
    protected Set<String> ingredients;
    protected Set<String> surfaces;
    protected double price;
    protected double efficiency;

    public Sanitizer(int noKilledOrganisms, Set<String> ingredients, Set<String> surfaces) {
        this.noKilledOrganisms = noKilledOrganisms;
        this.ingredients = Set.copyOf(ingredients);
        this.surfaces = Set.copyOf(surfaces);
    }

    double findPrice(){
        if(efficiency < 0.9) {
            return 10;
        }else if(efficiency < 0.95) {
            return 20;
        }else if(efficiency < 0.975){
            return 30;
        }else if(efficiency < 0.99){
            return 40;
        }else{
            return 50;
        }
    }
    @Override

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Sanitizer)) {
            return false;
        }
        Sanitizer other = (Sanitizer) obj;
        return this.noKilledOrganisms == other.noKilledOrganisms &&
                this.ingredients.equals(other.ingredients) &&
                this.surfaces.equals(other.surfaces) &&
                this.price == other.price &&
                this.efficiency == other.efficiency;
    }

    @Override
    public int compareTo(Sanitizer s){
        if(efficiency == s.efficiency){
            return Integer.compare(noKilledOrganisms, s.noKilledOrganisms);
        }else{
            return Double.compare(s.efficiency, efficiency);
        }
    }
}
