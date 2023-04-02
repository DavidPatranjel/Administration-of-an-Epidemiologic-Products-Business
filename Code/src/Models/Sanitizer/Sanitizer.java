package Models.Sanitizer;

import java.util.Set;

public abstract class Sanitizer {
    protected int noKilledOrganisms;
    protected Set<String> ingredients;
    protected Set<String> surfaces;
    protected double price;
    protected double efficiency;

    public Sanitizer(int noKilledOrganisms, Set<String> ingredients, Set<String> surfaces) {
        this.noKilledOrganisms = noKilledOrganisms;
        this.ingredients = ingredients;
        this.surfaces = surfaces;
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

}
