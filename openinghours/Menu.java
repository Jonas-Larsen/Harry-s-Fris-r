package openinghours;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private StoreHours store;
    private FinancialData financialData;


    public Menu(StoreHours store, FinancialData financialData) {
        this.store = store;
        this.financialData = financialData;
    }

    public void showMenu() {
        System.out.println("\nStore Opening Hours Menu:");
        System.out.println("1. Check current opening hours");
        System.out.println("2. Change opening hours");
        System.out.println("3. Show avaliable times");
        System.out.println("4. Configure Reservations");
        System.out.println("5. Show avaliable times for next five days");
        System.out.println("6. log in as accountant");
        System.out.println("7. Show Financial Data")
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    public void checkCurrentOpeningHours() {
        System.out.println("Current Opening Hours:");
        store.displayStoreHours();
    }

    public void changeOpeningHours(Scanner scanner) {
        System.out.print("Enter the day index (0-6) to change opening hours: ");
        int dayIndex = scanner.nextInt();
        scanner.nextLine(); // scanner bug

        System.out.print("Enter new opening hours: ");
        String newHours = scanner.nextLine();

        store.setOpeningHours(dayIndex, newHours);
        System.out.println("Opening hours updated.");
    }

    public void showAvaliableTimes(){
        FrisorBookingSystem bookingSystem = new FrisorBookingSystem();
        Date dato= new Date();


        List<Date> ledigeTider= bookingSystem.findLedigeTider(dato, 30);

        System.out.println("Ledige tider for " + dato);
        for (Date ledigTid : ledigeTider){
            System.out.println(ledigTid);
        }
    }

    public void configureReservations() {
        new ConfigureReservations().run();
    }
    
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public void showAvaliableTimesForNextFiveDays(){
        FrisorBookingSystem bookingSystem = new FrisorBookingSystem();
        Date currentDate = new Date();

        for (int i = 0; i < 5; i++){
            List<Date> avaliableTimes = bookingSystem.findLedigeTider(currentDate, 30);
            System.out.println("Avaliable times for " + currentDate + ":");
            for (Date avaliableTime : avaliableTimes){
                System.out.println(avaliableTime);
            }
            currentDate = addDays(currentDate, 1);
        }
    }

    public boolean validatePassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the password: ");
        String input = scanner.nextLine();
        if (input.equals("harryharry")) {
            System.out.println("Password is correct. Access granted.");
            return true; // Return true to indicate that the password is correct.
        } else {
            System.out.println("Incorrect password, try again.");
            return false; // Return false to indicate that the password is incorrect.
        }
    }

    public void displayFinancialInformation() {
        double currentMonthEarnings = financialData.getCurrentMonthEarnings();
        int currentMonthCustomers = financialData.getCurrentMonthCustomers();

        System.out.println("Financial Information:");
        System.out.printf("Current Month Earnings: %.2f kr%n", + currentMonthEarnings);
        System.out.println("Current Month Customers: " + currentMonthCustomers);
    }
}
}

