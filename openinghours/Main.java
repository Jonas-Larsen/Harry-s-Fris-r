package openinghours;

import java.util.Scanner;

public class Main {
    private Menu menu; // Define the 'menu' variable here

    public Main() {
        StoreHours store = new StoreHours();
        menu = new Menu(store);
    }

    public static void main(String[] args) {
        new Main().run();

    }

    private void run() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            menu.showMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    menu.checkCurrentOpeningHours();
                    break;
                case 2:
                    menu.changeOpeningHours(scanner);
                    break;
                case 3:
                    menu.showAvaliableTimes();
                    break;
                case 4:
                    menu.configureReservations();
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 4);


        scanner.close();
    }

}
