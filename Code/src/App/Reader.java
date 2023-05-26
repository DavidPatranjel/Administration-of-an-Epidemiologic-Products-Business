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

import javax.xml.crypto.Data;
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

    private void typeOfMasks(String op){
        System.out.println("What type of mask do you want to " + op + "?");
        System.out.println("1. Surgical Mask");
        System.out.println("2. Polycarbonate Mask");
    }
    public Mask readMask(){
        int op;
        Scanner reader = new Scanner(System.in);
        Mask mask;
        typeOfMasks("add");
        try {
            op = reader.nextInt();
            reader.nextLine();
            switch (op) {
                case 1 -> {
                    String pt, c;
                    int f;
                    System.out.print("Protection type: ");
                    pt = reader.nextLine();
                    System.out.print("Colour: ");
                    c = reader.nextLine();
                    System.out.print("Number of folds: ");
                    f = reader.nextInt();
                    reader.nextLine();
                    mask = new SurgicalMask(pt, c, f);
                }
                case 2 -> {
                    String pt, c, g;
                    int f;
                    System.out.print("Protection type: ");
                    pt = reader.nextLine();
                    System.out.print("Colour: ");
                    c = reader.nextLine();
                    System.out.print("Number of folds: ");
                    f = reader.nextInt();
                    reader.nextLine();
                    System.out.print("Grip type: ");
                    g = reader.nextLine();
                    mask = new PolycarbonateMask(pt, c, f, g);
                }

                default -> {
                    System.out.println("Invalid option!");
                    mask = null;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalit input!");
            return null;
        }

        return mask;
    }
    public int showMask(){
        int op;
        Scanner reader = new Scanner(System.in);
        typeOfMasks("show");
        try{
            op = reader.nextInt();
            reader.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
            return 0;
        }
        switch (op) {
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            default -> System.out.println("Invalid option!");
        }
        return 0;
    }
    public int deleteMask(){
        int op;
        Scanner reader = new Scanner(System.in);
        typeOfMasks("delete");
        try{
            op = reader.nextInt();
            reader.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
            return 0;
        }
        switch (op) {
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            default -> System.out.println("Invalid option!");
        }
        return 0;
    }


    private void typeOfSanitizer(String op){
        System.out.println("What type of sanitizer do you want to " + op + "?");
        System.out.println("1. Bacteria Sanitizer");
        System.out.println("2. Virus Sanitizer");
        System.out.println("3. Fungal Sanitizer");
    }

    public Sanitizer readSanitizer(){
        int op;
        Scanner reader = new Scanner(System.in);
        Sanitizer sanitizer;
        typeOfSanitizer("add");
        try {
            op = reader.nextInt();

            switch (op) {
                case 1 -> {
                    int k, n, m;
                    Set<String> ing = new HashSet<>(), s = new HashSet<>();

                    System.out.print("No organisms killed: ");
                    k = reader.nextInt();
                    System.out.print("No ingerdients: ");
                    n = reader.nextInt();
                    reader.nextLine();
                    System.out.println("Type the " + n + " ingerdients: ");
                    for (int i = 1; i <= n; i++) {
                        ing.add(reader.nextLine());
                    }
                    System.out.print("No surfaces: ");
                    m = reader.nextInt();
                    System.out.println("Type the " + m + " surfaces: ");
                    reader.nextLine();
                    for (int i = 1; i <= m; i++) {
                        s.add(reader.nextLine());
                    }

                    sanitizer = new BacteriaSanitizer(k, ing, s);
                }
                case 2 -> {
                    int k, n, m;
                    Set<String> ing = new HashSet<>(), s = new HashSet<>();

                    System.out.print("No organisms killed: ");
                    k = reader.nextInt();
                    System.out.print("No ingerdients: ");
                    n = reader.nextInt();
                    System.out.println("Type the " + n + " ingerdients: ");
                    reader.nextLine();
                    for (int i = 1; i <= n; i++) {
                        ing.add(reader.nextLine());
                    }
                    System.out.print("No surfaces: ");
                    m = reader.nextInt();
                    System.out.println("Type the " + m + " surfaces: ");
                    reader.nextLine();
                    for (int i = 1; i <= m; i++) {
                        s.add(reader.nextLine());
                    }

                    sanitizer = new VirusSanitizer(k, ing, s);
                }
                case 3 -> {
                    int k, n, m;
                    Set<String> ing = new HashSet<>(), s = new HashSet<>();

                    System.out.print("No organisms killed: ");
                    k = reader.nextInt();
                    System.out.print("No ingerdients: ");
                    n = reader.nextInt();
                    System.out.println("Type the " + n + " ingerdients: ");
                    reader.nextLine();
                    for (int i = 1; i <= n; i++) {
                        ing.add(reader.nextLine());
                    }
                    System.out.print("No surfaces: ");
                    m = reader.nextInt();
                    System.out.println("Type the " + m + " surfaces: ");
                    reader.nextLine();
                    for (int i = 1; i <= m; i++) {
                        s.add(reader.nextLine());
                    }

                    sanitizer = new FungalSanitizer(k, ing, s);
                }

                default -> {
                    System.out.println("Invalid option!");
                    sanitizer = null;
                }
            }
        }catch (InputMismatchException e){
            System.out.println("Invalit input!");
            return null;
        }

        return sanitizer;
    }
    public int showSanitizer(){
        int op;
        Scanner reader = new Scanner(System.in);
        typeOfSanitizer("show");
        try {
            op = reader.nextInt();
            reader.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
            return 0;
        }
        switch (op) {
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            case 3 -> {
                return 3;
            }
            default -> System.out.println("Invalid option!");
        }
        return 0;
    }
    public int deleteSanitizer(){
        int op;
        Scanner reader = new Scanner(System.in);
        typeOfSanitizer("delete");
        try {
            op = reader.nextInt();
            reader.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Invalid input!");
            return 0;
        }
        switch (op) {
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            case 3 -> {
                return 3;
            }
            default -> System.out.println("Invalid option!");
        }
        return 0;
    }

    public Client readClient(){
        String n,c,ct,cty,s; int nr;
        Scanner reader = new Scanner(System.in);
        try {
            System.out.print("Name of the client: ");
            n = reader.nextLine();
            System.out.print("Client CUI: ");
            c = reader.nextLine();
            System.out.print("Client Address - country: ");
            ct = reader.nextLine();
            System.out.print("Client Address - city: ");
            cty = reader.nextLine();
            System.out.print("Client Address - street: ");
            s = reader.nextLine();
            System.out.print("Client Address - number: ");
            nr = reader.nextInt();
            reader.nextLine();
        }catch (InputMismatchException e){
            System.out.println("Invalit input!");
            return null;
        }

        return new Client(n, c, ct ,cty, s, nr);
    }

    public int getIndexForUpdate(){
        Scanner reader = new Scanner(System.in);
        DataDictionary dataDictionary = DataDictionary.getInstance();
        boolean found = false;
        int index = -1;
        while(!found){
            System.out.print("Give object ID: ");
            index = reader.nextInt();
            if(dataDictionary.is_inClients(index)){
                found = true;
            }else{
                System.out.println("There is no client with this ID!");
            }
        }
        return index;
    }

    public Acquisition readAcquisition() {
        Scanner reader = new Scanner(System.in);
        Integer[] sgmasks = null, pcmasks = null, bcsanitizers = null, fgsanitizers = null, vssanitizers = null;
        int c, op;
        Acquisition acquisition;
        Service s = Service.getInstance();
        DataDictionary dataDictionary = DataDictionary.getInstance();
        int index, noMasks, noSanitizers;
        boolean check = true;

        if (dataDictionary.emptyClients()) {
            System.out.println("There are no clients");
            return null;
        }

        System.out.println("What is the Client for this acquisition?");
        s.listClients();
        do {
            try {
                System.out.print("Please insert the client index: ");
                index = reader.nextInt();
                if (index >= 0 && dataDictionary.is_inClients(index))
                    check = false;
                if (check) {
                    System.out.println("Not a valid index!");
                }
            }catch (InputMismatchException e){
                System.out.println("Not valid input!");
                return null;
            }
        } while (check);
        c = index;
        System.out.println("Added client index = " + c);

        if (dataDictionary.emptySurgicalMasksPrice() && dataDictionary.emptyPolycarbonateMasksPrice())
            System.out.println("There are no masks to be added");
        else {
            System.out.print("How many masks do you want to add? ");
            try {
                noMasks = reader.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Input error!");
                return null;
            }
            s.listMasks();
            for (int i = 1; i <= noMasks; i++) {
                try{
                    typeOfMasks("add in the acquisition");
                    op = reader.nextInt();
                }catch (InputMismatchException e){
                    System.out.println("Not valid input!");
                    return null;
                }
                switch (op) {
                    case 1 -> { ///surgical
                        if (dataDictionary.emptySurgicalMasksPrice()) {
                            System.out.println("There are no surgical masks!");
                            i--;
                        } else {
                            check = true;
                            do {
                                try{
                                    System.out.print("Please insert the surgical mask index for mask no " + i + ": ");
                                    index = reader.nextInt();
                                    if (index >= 0 && dataDictionary.is_inSurgicalMasksPrice(index))
                                        check = false;
                                    if (check) {
                                        System.out.println("Not a valid index!");
                                    }
                                }catch (InputMismatchException e){
                                    System.out.println("Not valid input!");
                                }
                            } while (check);

                            if (sgmasks == null) {
                                sgmasks = new Integer[1];
                                sgmasks[0] = index;
                            } else {
                                sgmasks = Arrays.copyOf(sgmasks, sgmasks.length + 1);
                                sgmasks[sgmasks.length - 1] = index;
                            }
                            System.out.println("Added surgical mask index = " + index);
                        }
                    }
                    case 2 -> {
                        if (dataDictionary.emptyPolycarbonateMasksPrice()) {
                            System.out.println("There are no polycarbonate masks!");
                            i--;
                        } else {
                            check = true;
                            do {
                                try {
                                    System.out.print("Please insert the polycarbonate mask index for mask no " + i + ": ");
                                    index = reader.nextInt();
                                    if (index >= 0 && dataDictionary.is_inPolycarbonateMasksPrice(index))
                                        check = false;
                                    if (check) {
                                        System.out.println("Not a valid index!");
                                    }
                                }catch (InputMismatchException e){
                                    System.out.println("Not valid input!");
                                }
                            } while (check);

                            if (pcmasks == null) {
                                pcmasks = new Integer[1];
                                pcmasks[0] = index;
                            } else {
                                pcmasks = Arrays.copyOf(pcmasks, pcmasks.length + 1);
                                pcmasks[pcmasks.length - 1] = index;
                            }
                            System.out.println("Added polycarbonate mask index = " + index);
                        }
                    }

                    default -> {
                        System.out.println("Invalid option!");
                        i--;
                    }
                }
            }
        }

        if (dataDictionary.emptyBacterialSanitizersPrice() &&
                dataDictionary.emptyFungalSanitizersPrice() &&
                dataDictionary.emptyVirusSanitizerPrice())
            System.out.println("There are no sanitizers to be added");
        else {
            try {
                System.out.print("How many sanitizers do you want to add? ");
                noSanitizers = reader.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Not valid input!");
                return null;
            }
            s.listSanitizers();
            for (int i = 1; i <= noSanitizers; i++) {
                try {
                    typeOfSanitizer("add in the acquisition");
                    op = reader.nextInt();
                }catch (InputMismatchException e){
                    System.out.println("Not valid input!");
                    return null;
                }
                switch (op) {
                    case 1 -> { ///bacteria
                        if (dataDictionary.emptyBacterialSanitizersPrice()) {
                            System.out.println("There are no bacteria sanitizers!");
                            i--;
                        } else {
                            check = true;
                            do {
                                try{
                                    System.out.print("Please insert the bacteria sanitizer index for sanitizer no " + i + ": ");
                                    index = reader.nextInt();
                                    if (index >= 0 && dataDictionary.is_inBacterialSanitizersPrice(index))
                                        check = false;
                                    if (check) {
                                        System.out.println("Not a valid index!");
                                    }
                                }catch (InputMismatchException e){
                                    System.out.println("Not valid input!");
                                }
                            } while (check);

                            if (bcsanitizers == null) {
                                bcsanitizers = new Integer[1];
                                bcsanitizers[0] = index;
                            } else {
                                bcsanitizers = Arrays.copyOf(bcsanitizers, bcsanitizers.length + 1);
                                bcsanitizers[bcsanitizers.length - 1] = index;
                            }
                            System.out.println("Added bacteria sanitizer index = " + index);
                        }
                    }
                    case 2 -> { ///virus
                        if (dataDictionary.emptyVirusSanitizerPrice()) {
                            System.out.println("There are no virus sanitizers!");
                            i--;
                        } else {
                            check = true;
                            do {
                                try {
                                    System.out.print("Please insert the virus sanitizer index for sanitizer no " + i + ": ");
                                    index = reader.nextInt();
                                    if (index >= 0 && dataDictionary.is_inVirusSanitizersPrice(index))
                                        check = false;
                                    if (check) {
                                        System.out.println("Not a valid index!");
                                    }
                                }catch (InputMismatchException e){
                                    System.out.println("Not valid input!");
                                }
                            } while (check);

                            if (vssanitizers == null) {
                                vssanitizers = new Integer[1];
                                vssanitizers[0] = index;
                            } else {
                                vssanitizers = Arrays.copyOf(vssanitizers, vssanitizers.length + 1);
                                vssanitizers[vssanitizers.length - 1] = index;
                            }
                            System.out.println("Added virus sanitizer index = " + index);
                        }
                    }
                    case 3 -> { ///fungal
                        if (dataDictionary.emptyFungalSanitizersPrice()) {
                            System.out.println("There are no fungal sanitizers!");
                            i--;
                        } else {
                            check = true;
                            do {
                                try{
                                    System.out.print("Please insert the fungal sanitizer index for sanitizer no " + i + ": ");
                                    index = reader.nextInt();
                                    if (index >= 0 && dataDictionary.is_inFungalSanitizersPrice(index))
                                        check = false;
                                    if (check) {
                                        System.out.println("Not a valid index!");
                                    }
                                }catch (InputMismatchException e){
                                    System.out.println("Not valid input!");
                                }
                            } while (check);

                            if (fgsanitizers == null) {
                                fgsanitizers = new Integer[1];
                                fgsanitizers[0] = index;
                            } else {
                                fgsanitizers = Arrays.copyOf(fgsanitizers, fgsanitizers.length + 1);
                                fgsanitizers[fgsanitizers.length - 1] = index;
                            }
                            System.out.println("Added fungal sanitizer index = " + index);
                        }
                    }
                    default -> {
                        System.out.println("Invalid option!");
                        i--;
                    }
                }
            }
        }

        reader.nextLine();
        Date d = new Date();
        check = true;
        String special;
        do {
            System.out.print("Is this a special command (Y/N)? ");
            special = reader.nextLine();
            if (special.equals("N") || special.equals("Y"))
                check = false;
            if (check) {
                System.out.println("Input not valid!");
            }
        } while (check);
        if (special.equals("N")) {
            acquisition = new Acquisition(sgmasks, pcmasks, bcsanitizers, fgsanitizers, vssanitizers, c, d);
        } else {
            String descr;
            System.out.print("What is the description of the special command? ");
            descr = reader.nextLine();
            acquisition = new Acquisition(sgmasks, pcmasks, bcsanitizers, fgsanitizers, vssanitizers, c, d, true, descr);
        }
        return acquisition;
    }

    public int readIndex(){
        Scanner reader = new Scanner(System.in);
        System.out.print("Give object ID: ");
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
