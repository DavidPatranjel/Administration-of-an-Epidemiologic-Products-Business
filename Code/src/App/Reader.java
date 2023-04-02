package App;

import Models.Mask.Mask;
import Models.Mask.PolycarbonateMask;
import Models.Mask.SurgicalMask;
import Models.Sanitizer.BacteriaSanitizer;
import Models.Sanitizer.FungalSanitizer;
import Models.Sanitizer.Sanitizer;
import Models.Sanitizer.VirusSanitizer;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
public final class Reader {
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
                int f; double pr;
                System.out.print("Protection type: "); pt = reader.next();
                System.out.print("Colour: "); c = reader.next();
                System.out.print("Number of folds: "); f = reader.nextInt();
                System.out.print("Price: "); pr = reader.nextDouble();
                mask = new SurgicalMask(pt, c, f, pr);
            }
            case 2 -> {
                String pt, c, g;
                int f; double pr;
                System.out.print("Protection type: "); pt = reader.next();
                System.out.print("Colour: "); c = reader.next();
                System.out.print("Number of folds: "); f = reader.nextInt();
                System.out.print("Price: "); pr = reader.nextDouble();
                System.out.print("Grip type: "); g = reader.next();
                mask = new PolycarbonateMask(pt, c, f, pr, g);
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
                System.out.println("Type the " + n + " ingerdients: " );
                for(int i = 1; i <= n; i++){
                    ing.add(reader.next());
                }
                System.out.print("No surfaces: "); m = reader.nextInt();
                System.out.println("Type the " + m + " surfaces: " );
                for(int i = 1; i <= m; i++){
                    s.add(reader.next());
                }

                sanitizer = new BacteriaSanitizer(k, ing, s);
            }
            case 2 -> {
                int k, n, m;
                Set<String> ing = new HashSet<String>(), s = new HashSet<String>();

                System.out.print("No organisms killed: "); k = reader.nextInt();
                System.out.print("No ingerdients: "); n = reader.nextInt();
                System.out.println("Type the " + n + " ingerdients: " );
                for(int i = 1; i <= n; i++){
                    ing.add(reader.next());
                }
                System.out.print("No surfaces: "); m = reader.nextInt();
                System.out.println("Type the " + m + " surfaces: " );
                for(int i = 1; i <= m; i++){
                    s.add(reader.next());
                }

                sanitizer = new VirusSanitizer(k, ing, s);
            }
            case 3 -> {
                int k, n, m;
                Set<String> ing = new HashSet<String>(), s = new HashSet<String>();

                System.out.print("No organisms killed: "); k = reader.nextInt();
                System.out.print("No ingerdients: "); n = reader.nextInt();
                System.out.println("Type the " + n + " ingerdients: " );
                for(int i = 1; i <= n; i++){
                    ing.add(reader.next());
                }
                System.out.print("No surfaces: "); m = reader.nextInt();
                System.out.println("Type the " + m + " surfaces: " );
                for(int i = 1; i <= m; i++){
                    s.add(reader.next());
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

}
