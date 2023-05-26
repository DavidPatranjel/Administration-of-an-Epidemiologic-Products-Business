package App;
import java.util.InputMismatchException;
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
        System.out.println("3. Show a mask by ID.");
        System.out.println("4. Delete a mask.");
        System.out.println("5. Add a sanitizer.");
        System.out.println("6. List all the sanitizers.");
        System.out.println("7. Show a sanitizer by ID.");
        System.out.println("8. Delete a sanitizer.");
        System.out.println("9. Add a client.");
        System.out.println("10. List all the clients.");
        System.out.println("11. Update a client.");
        System.out.println("12. Add an acquisition.");
        System.out.println("13. List all the acquisitions.");
        System.out.println("14. Show income in a given month.");
        System.out.println("15. Show VAT (19% of income) for a given year.");
        System.out.println("16. Show all sanitizers sorted desc. by efficiency and asc. by killed organisms.");
        System.out.println("17. Create tables.");
        ///System.out.println("18. Print all local data.");
        System.out.println("0. Exit");
        System.out.println("-----------------------------------------------");
    }
    public void runMenu(){
        Scanner reader = new Scanner(System.in);
        Service service = Service.getInstance();
        Reader objReader = Reader.getInstance();
        int op;

        do
        {
            textMenu();
            System.out.print("Please insert your option: ");
            op = reader.nextInt();
            reader.nextLine();
            System.out.println("-----------------------------------------------");
            switch (op) {
                case 1 -> service.addMask(objReader.readMask());
                case 2 -> service.listMasks();
                case 3-> service.showMask(objReader.showMask());
                case 4-> service.deleteMask(objReader.deleteMask());
                case 5 -> service.addSanitizer(objReader.readSanitizer());
                case 6 -> service.listSanitizers();
                case 7 -> service.showSanitizer(objReader.showSanitizer());
                case 8 -> service.deleteSanitizer(objReader.deleteSanitizer());
                case 9 -> service.addClient(objReader.readClient());
                case 10 -> service.listClients();
                case 11 -> {
                    try{
                    service.updateClient(objReader.getIndexForUpdate(), objReader.readClient());
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input!");
                    }
                }
                case 12 -> service.addAcquisition(objReader.readAcquisition());
                case 13 -> service.listAcquisitions();
                case 14 -> {
                    try {
                        service.incomeDate(objReader.readMonth(), objReader.readYear());
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input!");
                    }
                }
                case 15 -> {
                    try{
                        service.VAT(objReader.readYear());
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input!");
                    }
                }
                case 16 -> service.sortedSanitizers();
                case 17-> service.configureTables();
                ///case 18 -> service.printLocalData();
                case 0 -> {
                    service.closeConnection();
                    System.out.println("You left the app. Goodbye!");
                }

                default -> System.out.println("Invalid option!");
            }

        } while (op != 0);

        reader.close();
    }
}
