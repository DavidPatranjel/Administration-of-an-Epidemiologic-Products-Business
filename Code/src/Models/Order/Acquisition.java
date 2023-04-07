package Models.Order;

import App.CRUD;
import Models.Mask.Mask;
import Models.Person.Client;
import Models.Sanitizer.Sanitizer;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Acquisition {
    final private Mask[] masks;
    final private Sanitizer[] sanitizers;

    final private double totalPrice;
    final private Date date;

    final private Client client;
    final private Boolean specialOrder;
    final private String specialOrderDescription;
    final static private SimpleDateFormat dateForm = new SimpleDateFormat("EEE, MMM d, ''yy");

    public Acquisition(Mask[] masks, Sanitizer[] sanitizers, Date date,
                       Client client, Boolean specialOrder, String specialOrderDescription) {
        this.masks = Arrays.copyOf(masks, masks.length);
        this.sanitizers = Arrays.copyOf(sanitizers, sanitizers.length);
        this.date = date;
        this.client = new Client(client);
        this.specialOrder = specialOrder;
        this.specialOrderDescription = specialOrderDescription;

        double s = 0;
        for(Mask m:masks){
            s += m.getPrice();
        }
        for(Sanitizer san:sanitizers){
            s += san.getPrice();
        }
        if(specialOrder){
            s = s * 1.5;
        }

        this.totalPrice = s;
    }

    public Acquisition(Mask[] masks, Sanitizer[] sanitizers, Date date,
                       Client client) {
        this.masks = Arrays.copyOf(masks, masks.length);
        this.sanitizers = Arrays.copyOf(sanitizers, sanitizers.length);
        this.date = date;
        this.client = new Client(client);
        this.specialOrder = false;
        this.specialOrderDescription = "";

        double s = 0;
        for(Mask m:masks){
            s += m.getPrice();
        }
        for(Sanitizer san:sanitizers){
            s += san.getPrice();
        }

        this.totalPrice = s;
    }

    public Date getDate() {
        return date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        StringBuilder stringMaskBuilder = new StringBuilder();
        StringBuilder stringSanitizersBuilder = new StringBuilder();

        for(Mask m : masks) {
            stringMaskBuilder.append(m.toString() + "\n");
        }

        for(Sanitizer s : sanitizers) {
            stringSanitizersBuilder.append(s.toString() + "\n");
        }

        String stringMask = stringMaskBuilder.toString();
        String stringSanitizers = stringSanitizersBuilder.toString();

        return "Acquisition - " + dateForm.format(date) + "\n" +
                "Total Price =" + totalPrice + "\n" +
                "Masks\n" + stringMask +
                "Sanitizers\n" + stringSanitizers +
                "Client\n" + client + "\n" +
                "SpecialOrder=" + specialOrder +
                ", " + specialOrderDescription + "\n" +
                "------------------\n";
    }
}
