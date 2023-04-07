package App;

import Models.Mask.Mask;
import Models.Mask.PolycarbonateMask;
import Models.Mask.SurgicalMask;
import Models.Order.Acquisition;
import Models.Person.Client;
import Models.Sanitizer.BacteriaSanitizer;
import Models.Sanitizer.FungalSanitizer;
import Models.Sanitizer.Sanitizer;
import Models.Sanitizer.VirusSanitizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class Reader{
    private static Reader instance;
    private Reader() {}

    // static block initialization for exception handling
    static {
        try {
            instance = new Reader();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating Service singleton instance");
        }
    }

    public static Reader getInstance() {
        if (instance == null) {
            instance = new Reader();
        }
        return instance;
    }

    private void typeOfMasks(){
        System.out.println("What type of mask do you want to add?");
        System.out.println("1. Surgical Mask");
        System.out.println("2. Polycarbonate Mask");
    }
    public Mask readMask(){
        int op;
        Scanner reader = new Scanner(System.in);
        Mask mask;
        typeOfMasks();
        op = reader.nextInt();

        switch (op) {
            case 1 -> {
                String pt, c;
                int f;
                System.out.print("Protection type: "); pt = reader.nextLine();
                System.out.print("Colour: "); c = reader.nextLine();
                System.out.print("Number of folds: "); f = reader.nextInt();
                reader.nextLine();
                mask = new SurgicalMask(pt, c, f);
            }
            case 2 -> {
                String pt, c, g;
                int f;
                System.out.print("Protection type: "); pt = reader.nextLine();
                System.out.print("Colour: "); c = reader.nextLine();
                System.out.print("Number of folds: "); f = reader.nextInt();
                reader.nextLine();
                System.out.print("Grip type: "); g = reader.nextLine();
                mask = new PolycarbonateMask(pt, c, f, g);
            }

            default -> {
                System.out.println("Invalid option!");
                mask = null;
            }
        }

        return mask;
    }

    private void typeOfSanitizer(){
        System.out.println("What type of sanitizer do you want to add?");
        System.out.println("1. Bacteria Sanitizer");
        System.out.println("2. Virus Sanitizer");
        System.out.println("3. Fungal Sanitizer");
    }

    public Sanitizer readSanitizer(){
        int op;
        Scanner reader = new Scanner(System.in);
        Sanitizer sanitizer;
        typeOfSanitizer();
        op = reader.nextInt();

        switch (op) {
            case 1 -> {
                int k, n, m;
                Set<String> ing = new HashSet<String>(), s = new HashSet<String>();

                System.out.print("No organisms killed: "); k = reader.nextInt();
                System.out.print("No ingerdients: "); n = reader.nextInt();
                reader.nextLine();
                System.out.println("Type the " + n + " ingerdients: " );
                for(int i = 1; i <= n; i++){
                    ing.add(reader.nextLine());
                }
                System.out.print("No surfaces: "); m = reader.nextInt();
                System.out.println("Type the " + m + " surfaces: " );
                reader.nextLine();
                for(int i = 1; i <= m; i++){
                    s.add(reader.nextLine());
                }

                sanitizer = new BacteriaSanitizer(k, ing, s);
            }
            case 2 -> {
                int k, n, m;
                Set<String> ing = new HashSet<String>(), s = new HashSet<String>();

                System.out.print("No organisms killed: "); k = reader.nextInt();
                System.out.print("No ingerdients: "); n = reader.nextInt();
                System.out.println("Type the " + n + " ingerdients: " );
                reader.nextLine();
                for(int i = 1; i <= n; i++){
                    ing.add(reader.nextLine());
                }
                System.out.print("No surfaces: "); m = reader.nextInt();
                System.out.println("Type the " + m + " surfaces: " );
                reader.nextLine();
                for(int i = 1; i <= m; i++){
                    s.add(reader.nextLine());
                }

                sanitizer = new VirusSanitizer(k, ing, s);
            }
            case 3 -> {
                int k, n, m;
                Set<String> ing = new HashSet<String>(), s = new HashSet<String>();

                System.out.print("No organisms killed: "); k = reader.nextInt();
                System.out.print("No ingerdients: "); n = reader.nextInt();
                System.out.println("Type the " + n + " ingerdients: " );
                reader.nextLine();
                for(int i = 1; i <= n; i++){
                    ing.add(reader.nextLine());
                }
                System.out.print("No surfaces: "); m = reader.nextInt();
                System.out.println("Type the " + m + " surfaces: " );
                reader.nextLine();
                for(int i = 1; i <= m; i++){
                    s.add(reader.nextLine());
                }

                sanitizer = new FungalSanitizer(k, ing, s);
            }

            default -> {
                System.out.println("Invalid option!");
                sanitizer = null;
            }
        }

        return sanitizer;
    }

    public Client readClient(){
        String n,c,ct,cty,s; int nr;
        Scanner reader = new Scanner(System.in);

        System.out.print("Name of the client: "); n = reader.nextLine();
        System.out.print("Client CUI: "); c = reader.nextLine();
        System.out.print("Client Address - country: "); ct = reader.nextLine();
        System.out.print("Client Address - city: "); cty = reader.nextLine();
        System.out.print("Client Address - street: "); s = reader.nextLine();
        System.out.print("Client Address - number: "); nr = reader.nextInt();
        reader.nextLine();

        return new Client(n, c, ct ,cty, s, nr);
    }

    public Acquisition readAcquisition() throws CloneNotSupportedException {
        Scanner reader = new Scanner(System.in);
        Client c; Mask[] masks = null; Sanitizer[] sanitizers = null;
        Acquisition acquisition = null;
        Service s = Service.getInstance();
        int index, noMasks, noSanitizers; boolean check = true;

        if(s.getClientsSize() == 0){
            System.out.println("There are no clients");
            return null;
        }

        System.out.println("What is the Client for this acquisition?");
        s.listClients();
        do {
            System.out.print("Please insert the client index: "); index = reader.nextInt();
            if(index >= 0 && index < s.getClientsSize())
                check = false;
            if(check){
                System.out.println("Not a valid index!");
            }
        }while(check);
        c = new Client(s.getClient(index));
        System.out.println("Added client index = " + index)
        ;
        System.out.print("How many masks do you want to add? "); noMasks = reader.nextInt();
        for(int i = 1; i <= noMasks; i++){
            s.listMasks();
            check = true;
            do {
                System.out.print("Please insert the mask index for mask no " + i + ": "); index = reader.nextInt();
                if(index >= 0 && index < s.getMasksSize())
                    check = false;
                if(check){
                    System.out.println("Not a valid index!");
                }
            }while(check);

            Mask newMask = (Mask) s.getMask(index).clone();
            if (masks == null){
                masks = new Mask[1];
                masks[0] = newMask;
            } else {
                masks = Arrays.copyOf(masks, masks.length + 1);
                masks[masks.length - 1] = newMask;
            }
            System.out.println("Added mask index = " + index);
        }

        System.out.print("How many sanitizers do you want to add? "); noSanitizers = reader.nextInt();
        for(int i = 1; i <= noSanitizers; i++) {
            s.listSanitizers();
            check = true;
            do {
                System.out.print("Please insert the sanitizer index for sanitizer no " + i + ": ");
                index = reader.nextInt();
                if (index >= 0 && index < s.getSanitizersSize())
                    check = false;
                if (check) {
                    System.out.println("Not a valid index!");
                }
            } while (check);

            Sanitizer newSanitizer = (Sanitizer) s.getSanitizer(index).clone();
            if (sanitizers == null) {
                sanitizers = new Sanitizer[1];
                sanitizers[0] = newSanitizer;
            } else {
                sanitizers = Arrays.copyOf(sanitizers, sanitizers.length + 1);
                sanitizers[sanitizers.length - 1] = newSanitizer;
            }
            System.out.println("Added sanitizer index = " + index);
        }
        reader.nextLine();

        Date d = new Date();
        check = true;
        String special;
        do {
            System.out.print("Is this a special command (Y/N)? "); special = reader.nextLine();
            if(special.equals("N") || special.equals("Y"))
                check = false;
            if(check){
                System.out.println("Input not valid!");
            }
        }while(check);

        if(special.equals("N")){
            acquisition = new Acquisition(masks, sanitizers, d, c);
        }else{
            String descr;
            System.out.print("What is the description of the special command? "); descr = reader.nextLine();
            acquisition = new Acquisition(masks, sanitizers, d, c, true, descr);
        }
        return acquisition;
    }


    public Mask[] readMasksFromFile() throws FileNotFoundException {
        File m1 = new File("Code/src/Files/surgicalmasks.in");
        File m2 = new File("Code/src/Files/polycarbonatemasks.in");
        Scanner reader1 = new Scanner(m1);
        Scanner reader2 = new Scanner(m2);

        Mask[] masks = null;

        while (reader1.hasNextLine()){
            String line = reader1.nextLine();
            String[] fields = line.strip().split(",");
            Mask m = new SurgicalMask(fields[0], fields[1], Integer.parseInt(fields[2]));
            if (masks == null) {
                masks = new Mask[1];
                masks[0] = m;
            } else {
                if (!isMaskAlreadyInList(masks, m)) {
                    masks = Arrays.copyOf(masks, masks.length + 1);
                    masks[masks.length - 1] = m;
                }
            }
        }
        reader1.close();

        while (reader2.hasNextLine()){
            String line = reader2.nextLine();
            String[] fields = line.strip().split(",");
            Mask m = new PolycarbonateMask(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3]);
            if (masks == null) {
                masks = new Mask[1];
                masks[0] = m;
            } else {
                if (!isMaskAlreadyInList(masks, m)) {
                    masks = Arrays.copyOf(masks, masks.length + 1);
                    masks[masks.length - 1] = m;
                }
            }
        }
        reader2.close();

        return masks;
    }

    private boolean isMaskAlreadyInList(Mask[] masks, Mask m) {
        for (Mask recorderMask: masks){
            if (recorderMask.equals(m)){
                return true;
            }
        }
        return false;
    }

    /*for the future => factory dp*/
    public Sanitizer[] readSanitizerFromFile() throws FileNotFoundException {
        File m1 = new File("Code/src/Files/bacteriasanitizers.in");
        File m2 = new File("Code/src/Files/fungalsanitizers.in");
        File m3 = new File("Code/src/Files/virussanitizers.in");
        Scanner reader1 = new Scanner(m1);
        Scanner reader2 = new Scanner(m2);
        Scanner reader3 = new Scanner(m3);

        Sanitizer[] sanitizers = null;

        while (reader1.hasNextLine()){
            String line = reader1.nextLine();
            String[] fields = line.strip().split(",");
            int n, m;
            Set<String> ing = new HashSet<String>(), sur = new HashSet<String>();

            n = Integer.parseInt(fields[1]);
            for(int i = 1; i <= n; i++){
                ing.add(fields[1 + i]);
            }
            m = Integer.parseInt(fields[1 + n + 1]);
            for(int i = 1; i <= m; i++){
                sur.add(fields[1 + n + 1 + i]);
            }

            Sanitizer s = new BacteriaSanitizer(Integer.parseInt(fields[0]), ing, sur);
            if (sanitizers == null) {
                sanitizers = new Sanitizer[1];
                sanitizers[0] = s;
            } else {
                if (!isSanitizerAlreadyInList(sanitizers, s)) {
                    sanitizers = Arrays.copyOf(sanitizers, sanitizers.length + 1);
                    sanitizers[sanitizers.length - 1] = s;
                }
            }
        }
        reader1.close();

        while (reader2.hasNextLine()){
            String line = reader2.nextLine();
            String[] fields = line.strip().split(",");
            int n, m;
            Set<String> ing = new HashSet<String>(), sur = new HashSet<String>();

            n = Integer.parseInt(fields[1]);
            for(int i = 1; i <= n; i++){
                ing.add(fields[1 + i]);
            }
            m = Integer.parseInt(fields[1 + n + 1]);
            for(int i = 1; i <= m; i++){
                sur.add(fields[1 + n + 1 + i]);
            }

            Sanitizer s = new FungalSanitizer(Integer.parseInt(fields[0]), ing, sur);
            if (sanitizers == null) {
                sanitizers = new Sanitizer[1];
                sanitizers[0] = s;
            } else {
                if (!isSanitizerAlreadyInList(sanitizers, s)) {
                    sanitizers = Arrays.copyOf(sanitizers, sanitizers.length + 1);
                    sanitizers[sanitizers.length - 1] = s;
                }
            }
        }
        reader2.close();

        while (reader3.hasNextLine()){
            String line = reader3.nextLine();
            String[] fields = line.strip().split(",");
            int n, m;
            Set<String> ing = new HashSet<String>(), sur = new HashSet<String>();

            n = Integer.parseInt(fields[1]);
            for(int i = 1; i <= n; i++){
                ing.add(fields[1 + i]);
            }
            m = Integer.parseInt(fields[1 + n + 1]);
            for(int i = 1; i <= m; i++){
                sur.add(fields[1 + n + 1 + i]);
            }

            Sanitizer s = new VirusSanitizer(Integer.parseInt(fields[0]), ing, sur);
            if (sanitizers == null) {
                sanitizers = new Sanitizer[1];
                sanitizers[0] = s;
            } else {
                if (!isSanitizerAlreadyInList(sanitizers, s)) {
                    sanitizers = Arrays.copyOf(sanitizers, sanitizers.length + 1);
                    sanitizers[sanitizers.length - 1] = s;
                }
            }
        }
        reader3.close();

        return sanitizers;
    }

    private boolean isSanitizerAlreadyInList(Sanitizer[] sanitizers, Sanitizer s) {
        for (Sanitizer recorderSanitizer: sanitizers){
            if (recorderSanitizer.equals(s)){
                return true;
            }
        }
        return false;
    }

    public Client[] readClientsFromFile() throws FileNotFoundException {
        File m1 = new File("Code/src/Files/clients.in");
        Scanner reader1 = new Scanner(m1);

        Client[] clients = null;

        while (reader1.hasNextLine()){
            String line = reader1.nextLine();
            String[] fields = line.strip().split(",");
            Client c = new Client(fields[0], fields[1], fields[2], fields[3],
                    fields[4], Integer.parseInt(fields[5]));
            if (clients == null) {
                clients = new Client[1];
                clients[0] = c;
            } else {
                if (!isClientAlreadyInList(clients, c)) {
                    clients = Arrays.copyOf(clients, clients.length + 1);
                    clients[clients.length - 1] = c;
                }
            }
        }
        reader1.close();

        return clients;
    }

    private boolean isClientAlreadyInList(Client[] clients, Client c) {
        for (Client recorderClient: clients){
            if (recorderClient.equals(c)){
                return true;
            }
        }
        return false;
    }

    public int readIndex(){
        Scanner reader = new Scanner(System.in);
        System.out.print("Give object index: ");
        return reader.nextInt();
    }

    public int readMonth(){
        Scanner reader = new Scanner(System.in);
        int ans;
        System.out.print("Insert month (1-12): "); ans = reader.nextInt();
        while(!(1 <= ans && ans <= 12)){
            System.out.println("Input not valid!");
            System.out.print("Insert month (1-12): "); ans = reader.nextInt();
        }
        reader.nextLine();
        return ans;
    }

    public int readYear(){
        Scanner reader = new Scanner(System.in);
        int ans;
        System.out.print("Insert year: "); ans = reader.nextInt();
        while(!(1900 <= ans && ans <= 3000)){
            System.out.println("Input not valid!");
            System.out.print("Insert year: "); ans = reader.nextInt();
        }
        reader.nextLine();
        return ans;
    }
}
