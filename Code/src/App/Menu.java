package App;
import java.util.Scanner;

public final class Menu {
    private static Menu instance;
    private Menu() {}

    // static block initialization for exception handling
    static {
        try {
            instance = new Menu();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating singleton instance");
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
        System.out.println("ADMINISTRATION OF AN EPIDEMIC PRODUCTS BUSINESS - DAVID PATRANJEL 251");
        System.out.println("Here are all the actions you can choose from:");
        System.out.println("1. ");
        System.out.println("2. ");
        System.out.println("0. Exit");
        System.out.println("-------------------------------------");
    }
    public void runMenu(){
        Scanner reader = new Scanner(System.in);
        Service service = new Service();
        int op;

        do
        {
            textMenu();
            System.out.print("Please insert your option: ");
            op = reader.nextInt();
            reader.nextLine();

            switch (op) {
                case 0 -> {
                    System.out.println("You left the app. Goodbye!");
                }

                default -> System.out.println("Invalid option!");
            }

        } while (op != 0);

    }
}
