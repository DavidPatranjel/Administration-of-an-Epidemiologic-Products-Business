package Models.Order;

import App.CRUD;
import Models.Mask.Mask;
import Models.Person.Client;
import Models.Sanitizer.Sanitizer;

import java.util.Arrays;
import java.util.Date;

public class Acquisition {
    final private Mask[] masks;
    final private Sanitizer[] sanitizers;

    final private double totalPrice;
    final private Date date;

    final private Client client;
    private Boolean specialOrder;
    private String specialOrderDescription;

    public Acquisition(Mask[] masks, Sanitizer[] sanitizers, double totalPrice, Date date,
                       Client client, Boolean specialOrder, String specialOrderDescription) {
        this.masks = masks;
        this.sanitizers = sanitizers;
        this.totalPrice = totalPrice;
        this.date = date;
        this.client = client;
        this.specialOrder = specialOrder;
        this.specialOrderDescription = specialOrderDescription;
    }

    public Acquisition(Mask[] masks, Sanitizer[] sanitizers, double totalPrice, Date date,
                       Client client) {
        this.masks = masks;
        this.sanitizers = sanitizers;
        this.totalPrice = totalPrice;
        this.date = date;
        this.client = client;
    }
}
