import java.util.*;
import java.io.*;
import java.time.*;

public class ConfigureReservations {
    Scanner console = new Scanner(System.in);
    HashMap<String, Reservation> reservationList = new HashMap<String, Reservation>();

    public void run() {
        loadReservationList();
        System.out.print("""
            You have 2 options
            
            \t1) Add reservation
            \t2) Remove reservation
            
            """);
        
        whichOption();
        writeToFile();
    }

    private void writeToFile() {
        try {
            FileWriter fileWriter = new FileWriter(new File("ReservationList.csv"));
            for (Reservation r : reservationList.values()) {
                fileWriter.write(r.toString() + "\n");
            }
            fileWriter.close();
        } catch (Exception e) {}
    }

    private void loadReservationList() {
        try {
            Scanner file = new Scanner(new File("ReservationList.csv"));
            while (file.hasNextLine()) {
                Reservation reservation = new Reservation();
                Scanner currentLine = new Scanner (file.nextLine());
                currentLine.useDelimiter(";");
                reservation.setName(currentLine.next());
                reservation.setTime(LocalDateTime.parse(currentLine.next()));
                reservationList.put(reservation.getName(), reservation);
            }
            file.close();
        } catch (Exception e) {}
    }
    
    private void whichOption() {
        System.out.print("Choose option (0 to exit) [1-2]: ");
        String choice = console.nextLine().strip();
        
        switch (choice) {
            case "0":
                break;
            case "1":
                addReservation();
                break;
            case "2":
                removeReservation();
                break;
            default:
                whichOption();
                return;
        }
    }

    private void overwriteOrNot(String name) {
        System.out.print("Do you want to overwrite reservation? [Y/n]: ");
            String pick = console.nextLine().toLowerCase();

            switch (pick) {
                case "", "y":
                    finishAddingReservation(name);
                    return;
                case "n":
                    addReservation();
                    return;
                default:
                    overwriteOrNot(name);
                    return;
            }
    }
    
    private void addReservation() {
        
        System.out.print("Add name: ");
        String name = console.nextLine();
        if (reservationList.containsKey(name)) {
            System.out.println("Customer already has reservation.");
            overwriteOrNot(name);
        } else {
            finishAddingReservation(name);
        }
    }

    private void finishAddingReservation(String name) {
        Reservation reservation = new Reservation();
        reservation.setName(name);
        System.out.print("Choose date (yyyy-MM-dd): ");
        String date = console.nextLine();
        System.out.print("Choose time of day (HH:mm): ");
        String time = console.nextLine();

        try {
            reservation.setTime(LocalDateTime.parse(date + "T" + time));
            reservationList.put(reservation.getName(), reservation);
        } catch (Exception e) {
            System.out.println("Date and/or time not valid.");
            addReservation();
            return;
        }
    }
    
    private void removeReservation() {
        System.out.print("Choose customer to remove: ");
        String removal = console.nextLine();
        if (reservationList.containsKey(removal)) {
            reservationList.remove(removal);
        } else {
            System.out.println("Reservation doesn't exist.");
            removeReservation();
            return;
        }
        
    }
    
}