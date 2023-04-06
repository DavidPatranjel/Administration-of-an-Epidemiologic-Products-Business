package App;

import Models.Mask.Mask;
import Models.Sanitizer.Sanitizer;
import Models.Person.Client;
import Models.Order.Acquisition;

import java.util.Arrays;

import static java.util.Arrays.sort;

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

    public void setMasks(Mask[] masks) {
        this.masks = Arrays.copyOf(masks, masks.length);
    }

    public void setSanitizers(Sanitizer[] sanitizers) {
        this.sanitizers = Arrays.copyOf(sanitizers, sanitizers.length);
    }

    public void setClients(Client[] clients) {
        this.clients = Arrays.copyOf(clients, clients.length);
    }

    public void setAcquisitions(Acquisition[] acquisitions) {
        this.acquisitions = Arrays.copyOf(acquisitions, acquisitions.length);
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
                if (a.equals(newMask)){
                    System.out.println("This mask already exists.");
                    return;
                }
            }
            masks = Arrays.copyOf(masks, masks.length + 1);
            masks[masks.length - 1] = newMask;
        }
        System.out.println("You added a new mask successfully.");
    }

    public void listMasks(){
        if (masks == null){
            System.out.println("There are no masks.");
        }else {
            int k = 0;

            System.out.println("Here are all the masks.");
            for (Mask m : masks) {
                System.out.println(k++ + ") " + m);
            }
        }
    }

    public void deleteMask(int index){
        if (masks == null){
            System.out.println("There are no masks yet.");
            return;
        }

        if(index < 0 || index >= masks.length){
            System.out.println("Could't find mask with index " + index);
        }else{
            for (int i = index; i < masks.length - 1; i++){
                masks[i] = masks[i + 1];
            }
            masks = Arrays.copyOf(masks, masks.length - 1);
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
                if (a.equals(newSanitizer)){
                    System.out.println("This sanitizer already exists.");
                    return;
                }
            }
            sanitizers = Arrays.copyOf(sanitizers, sanitizers.length + 1);
            sanitizers[sanitizers.length - 1] = newSanitizer;
        }
        System.out.println("You added a new sanitizer successfully.");
    }

    public void listSanitizers(){
        if (sanitizers == null){
            System.out.println("There are no sanitizers.");
        }else {
            int k = 0;
            System.out.println("Here are all the sanitizers.");
            for (Sanitizer s: sanitizers) {
                System.out.println(k++ + ")" + s);
            }
        }
    }

    public void deleteSanitizer(int index){
        if (sanitizers == null){
            System.out.println("There are no sanitizers yet.");
            return;
        }

        if(index < 0 || index >= sanitizers.length){
            System.out.println("Could't find sanitizer with index " + index);
        }else{
            for (int i = index; i < sanitizers.length - 1; i++){
                sanitizers[i] = sanitizers[i + 1];
            }
            sanitizers = Arrays.copyOf(sanitizers, sanitizers.length - 1);
        }
    }

    public void addClient(Client newClient){
        if(newClient == null){
            System.out.println("Error in creating client!");
            return;
        }

        if (clients == null){
            clients = new Client[1];
            clients[0] = newClient;
        } else {
            for (Client c : clients){
                if (c.equals(newClient)){
                    System.out.println("This client already exists.");
                    return;
                }
            }
            clients = Arrays.copyOf(clients, clients.length + 1);
            clients[clients.length - 1] = newClient;
        }
        System.out.println("You added a new client successfully.");
    }

    public void listClients(){
        if (clients == null){
            System.out.println("There are no clients.");
        }else {
            int k = 0;
            System.out.println("Here are all the clients.");
            for (Client c : clients) {
                System.out.println(k++ + ")" + c);
            }
        }
    }

    public void sortedSanitizers(){
        if(sanitizers == null){
            System.out.println("There are no sanitizers to be sorted");
            return;
        }

        Sanitizer[] sortedSanitizers = Arrays.copyOf(sanitizers, sanitizers.length);
        sort(sortedSanitizers);

        int k = 0;
        System.out.println("Here are all the sorted sanitizers.");
        for (Sanitizer s: sortedSanitizers) {
            System.out.println(k++ + ")" + s);
        }
    }
}
