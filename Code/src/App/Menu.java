package App;
import java.io.FileNotFoundException;
import java.util.Scanner;

public final class Menu {
    private static Menu instance;
    private Menu() {}

    // static block initialization for exception handling
    static {
        try {
            instance = new Menu();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating Menu singleton instance");
        }
    }
    public static Menu getInstance() {
        if (instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    private void textMenu()
    {
        System.out.println("-----------------------------------------------");
        System.out.println("ADMINISTRATION OF AN EPIDEMIC PRODUCTS BUSINESS - DAVID PATRANJEL 251");
        System.out.println("Here are all the actions you can choose from:");
        System.out.println("1. Add a mask.");
        System.out.println("2. List all the masks.");
        System.out.println("3. Delete a mask.");
        System.out.println("4. Add a sanitizer.");
        System.out.println("5. List all the sanitizers.");
        System.out.println("6. Delete a sanitizer.");
        System.out.println("7. Add a client.");
        System.out.println("8. List all the clients.");
        System.out.println("9. Add a acquisition.");
        System.out.println("10. List all the acquisitions.");
        System.out.println("11. Show income in a given month.");
        System.out.println("12. Show TVA (19% of income) for a given year.");
        System.out.println("13. Show all sanitizers sorted desc. by efficiency and asc. by killed organisms.");
        System.out.println("14. Read test input.");
        System.out.println("0. Exit");
        System.out.println("-----------------------------------------------");
    }
    public void runMenu() throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);
        Service service = Service.getInstance();
        Reader objReader = Reader.getInstance();
        int op;

        do
        {
            textMenu();
            System.out.print("Please insert your option: ");
            op = reader.nextInt();
            ///reader.nextLine();
            System.out.println("-----------------------------------------------");
            switch (op) {
                case 1 -> {
                    service.addMask(objReader.readMask());
                }
                case 2 -> {
                    service.listMasks();
                }
                case 3-> {
                    service.deleteMask(objReader.readIndex());
                }
                case 4 -> {
                    service.addSanitizer(objReader.readSanitizer());
                }
                case 5 -> {
                    service.listSanitizers();
                }
                case 6 -> {
                    service.deleteSanitizer(objReader.readIndex());
                }
                case 7 -> {
                    service.addClient(objReader.readClient());
                }
                case 8 -> {
                    service.listClients();
                }
                case 9 -> {
                    System.out.println("create acquisition");
                }
                case 10 -> {
                    System.out.println("list acquisitions");
                }
                case 11 -> {
                    System.out.println("print income/month");
                }
                case 12 -> {
                    System.out.println("print tva/year");
                }
                case 13 -> {
                    service.sortedSanitizers();
                }
                case 14 -> {
                    service.setMasks(objReader.readMasksFromFile());
                    service.setSanitizers(objReader.readSanitizerFromFile());
                    service.setClients(objReader.readClientsFromFile());
                    System.out.println("All the data has been read from the files.");
                }
                case 0 -> {
                    System.out.println("You left the app. Goodbye!");
                }

                default -> System.out.println("Invalid option!");
            }

        } while (op != 0);

        reader.close();
    }
}
