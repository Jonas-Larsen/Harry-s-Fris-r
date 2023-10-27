package openinghours;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private StoreHours store;

    public Menu(StoreHours store) {
        this.store = store;
    }

    public void showMenu() {
        System.out.println("\nStore Opening Hours Menu:");
        System.out.println("1. Check current opening hours");
        System.out.println("2. Change opening hours");
        System.out.println("3. Show avaliable times");
        System.out.println("4. Configure Reservations");
        System.out.println("5. Exit");
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
}

