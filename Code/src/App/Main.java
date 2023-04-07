package App;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, CloneNotSupportedException {
        Menu menu = Menu.getInstance();
        menu.runMenu();
    }
}