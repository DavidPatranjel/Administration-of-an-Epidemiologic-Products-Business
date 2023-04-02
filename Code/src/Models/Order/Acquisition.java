package Models.Order;

import App.CRUD;
import Models.Mask.Mask;
import Models.Person.Client;
import Models.Sanitizer.Sanitizer;

import java.util.Arrays;
import java.util.Date;

public class Acquisition implements CRUD {
    private Mask[] masks;
    private Sanitizer[] sanitizers;

    private double totalPrice;
    private final Date date;

    private Client client;
    private Boolean specialOrder;
    private String specialOrderDescription;

    public Acquisition(Date date, Client client) {
        this.date = date;
        this.client = client;
        this.specialOrder = false;
        this.specialOrderDescription = "";
    }

    public Acquisition(Date date, Client client, Boolean specialOrder, String specialOrderDescription) {
        this.date = date;
        this.client = client;
        this.specialOrder = specialOrder;
        this.specialOrderDescription = specialOrderDescription;
    }

    public void addMask(Mask newMask){
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

    public void addSanitizer(Sanitizer newSanitizer){
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
}
