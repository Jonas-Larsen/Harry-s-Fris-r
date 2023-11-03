package openinghours;

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
                //det nye jeg havde added
                reservation.setBetaling(currentLine.next());
                reservation.setVare(currentLine.next());
                String pris = null;

                if (vare.equals("ingen")) {
                    pris = "100";
                } else if (vare.equals("shampo,brush")) {
                    pris = "250";
                } else if (vare.equals("brush")) {
                    pris = "200";
                } else if (vare.equals("shampo")) {
                    pris = "150";
                }
                reservation.setPris(pris);
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
            return;
        }
        
        finishAddingReservation(name);
    }

    private void finishAddingReservation(String name) {
        Reservation reservation = new Reservation();
        reservation.setName(name);
        System.out.print("Choose date (yyyy-MM-dd): ");
        String date = console.nextLine();

        var dayValue = LocalDate.parse(date).getDayOfWeek().getValue();
        var store = new StoreHours(null);
        var openingHours = store.getOpeningHours();
        String day = openingHours[dayValue];

        if (day.equals("Closed")) {
            System.out.println("Store is closed on the chosen date.");
            finishAddingReservation(name);
            return;
        }

        var openTime = LocalTime.parse(day.substring(0, 4));
        var closeTime = LocalTime.parse(day.substring(11, 14));
        var noon = LocalTime.of(12, 0);
            
        if (day.substring(6, 7).equals("PM")) {
            openTime = openTime.plusHours(noon.getHour());
        }
        if (day.substring(16, 17).equals("PM")) {
                closeTime = closeTime.plusHours(noon.getHour());
        }

        System.out.print("Choose time of day (HH:mm): ");
        String time = console.nextLine();

        if (LocalTime.parse(time).isBefore(openTime) || LocalTime.parse(time).isAfter(closeTime)) {
            System.out.println("Chosen time is not within opening hours.");
            finishAddingReservation(name);
            return;
        }

        //det nye jeg havde added
        System.out.print("Chose produkt to buy: shampo,brush (skriv i format shampo,brush eller brush eller shampo");
        String vare = console.nextLine();
        System.out.print("Do he pay in Credit or Har betalt (Skriv en af dem)");
        String betaling = console.nextLine();

        //det nye jeg havde added
                if (vare.equals("ingen")) {
            pris = "100";
        } else if (vare.equals("shampo,brush")) {
            pris = "250";
        } else if (vare.equals("brush")) {
            pris = "200";
        } else if (vare.equals("shampo")) {
            pris = "150";
        }
        reservation.setPris(pris);
        
        try {
            reservation.setTime(LocalDateTime.parse(date + "T" + time));
            reservation.setVare(vare);
            reservation.setBetaling(betaling);
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
