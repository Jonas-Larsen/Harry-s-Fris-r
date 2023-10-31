package openinghours;

import java.util.Scanner;

public class Main {
    private Menu menu; // Define the 'menu' variable here
    private FinancialData financialData;
    private frisørloging logind;
    

    public static void main(String[] args) throws InterruptedException {
        new Main().run();

    }

    private void run() throws InterruptedException {
        
    
        StoreHours store = new StoreHours();
        this.logind = new frisørloging("noget", "noget");
        financialData = new FinancialData();
        menu = new Menu(store, financialData);
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
                    menu.showAvaliableTimesForNextFiveDays();
                case 6:
                    this.logind.logind();
                    break;
                case 7:
                    if (menu.validatePassword()) {
                        menu.displayFinancialInformation();
                    }
                    break;
                case 8:
                    menu.configureCredit();
                    break;
                case 9:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 9);


        scanner.close();
    }
}

