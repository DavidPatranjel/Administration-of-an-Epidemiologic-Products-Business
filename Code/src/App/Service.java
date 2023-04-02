package App;

import Models.Mask.Mask;
import Models.Sanitizer.Sanitizer;
import Models.Person.Client;
import Models.Order.Acquisition;

import java.util.Arrays;

public final class Service implements CRUD {
    private Mask[] masks;
    private Sanitizer[] sanitizers;
    private Client[] clients;
    private Acquisition[] acquisitions;
    private static Service instance;
    private Service() {}

    // static block initialization for exception handling
    static {
        try {
            instance = new Service();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating Service singleton instance");
        }
    }

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    public void addMask(Mask newMask){
        if(newMask == null){
            System.out.println("Error in creating mask!");
            return;
        }

        if (masks == null){
            masks = new Mask[1];
            masks[0] = newMask;
        } else {
            for (Mask a : masks){
                if (a.equals(newMask)) return;
            }
            masks = Arrays.copyOf(masks, masks.length + 1);
            masks[masks.length - 1] = newMask;
        }
    }

    public void listMasks(){
        if (masks == null){
            System.out.println("There are no masks.");
        }else {
            System.out.println("Here are all the masks.");
            for (Mask m : masks) {
                System.out.println(m);
            }
        }
    }

    public void addSanitizer(Sanitizer newSanitizer){
        if(newSanitizer == null){
            System.out.println("Error in creating sanitizer!");
            return;
        }

        if (sanitizers == null){
            sanitizers = new Sanitizer[1];
            sanitizers[0] = newSanitizer;
        } else {
            for (Sanitizer a : sanitizers){
                if (a.equals(newSanitizer)) return;
            }
            sanitizers = Arrays.copyOf(sanitizers, sanitizers.length + 1);
            sanitizers[sanitizers.length - 1] = newSanitizer;
        }
    }

    public void listSanitizers(){
        if (sanitizers == null){
            System.out.println("There are no sanitizers.");
        }else {
            System.out.println("Here are all the sanitizers.");
            for (Sanitizer s: sanitizers) {
                System.out.println(s);
            }
        }
    }

}
