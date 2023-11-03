package openinghours;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class frisørloging {
    private String username;
    private String password;

    frisørloging(String username,String password){
        setPassword(password);
        setUsername(username);

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean equals(frisørloging obj){
        if(this.password.equals(obj.password)&&this.username.equals(obj.username)){
            return true;
        }else{
            return false;
        }
    }

    public void readOgAddUpEfter(){
        Scanner scanner = new Scanner(System.in);
        int penge = 0;

        int skylder = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("ReservationList.csv"));
            String line;
            System.out.println("Hvilken dato vil du starte fra? (yyyy-MM-dd)");
            String inputDate = scanner.nextLine();

            // Konverter inputdatoen til en LocalDate
            LocalDate startDate = LocalDate.parse(inputDate);

            System.out.println("hvor mange dage vil du se frem?");
            int datofrem = scanner.nextInt();
            LocalDate endDate = startDate.plusDays(datofrem);

            // Konverter start- og slutdatoer til strenge
            String startDateStr = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endDateStr = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            while ((line = bufferedReader.readLine()) != null) {
                // Her ser jeg på filen og ændre derefter CSV filen til en array med de splittet parts pr linje. Derefter Konverter datoen fra CSV til en LocalDate så den kan læse
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    String dateString = parts[1];

                    int etKlipning = 100;
                    LocalDate reservationDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    //tjekker om datoen er immellem de 2 datoer jeg har lavet, aka is before er før og isafter er efter
                    if (!reservationDate.isBefore(startDate) && !reservationDate.isAfter(endDate)) {
                        System.out.println(line);
                    }

                    //har bare lavet lidt om på det og nu er der så 3 kolerner, der tjekker om har betalt, kan nok konventere beløb når vi endelig får det lavet
                    if (line.contains("Har betalt")) {
                        if (line.contains("null")) {
                            penge += etKlipning;
                        } else if (line.contains("shampo,brush")) {
                            penge += 150+etKlipning;
                        } else if (!line.contains("shampo") && line.contains("brush")) {
                            penge += 100+etKlipning;
                        } else if (line.contains("shampo") && !line.contains("brush")) {
                            penge += 50+etKlipning;
                        }
                    } else if (line.contains("Credit")) {
                        if (line.contains("null")) {
                            skylder += 100+etKlipning;
                        } else if (line.contains("shampo,brush")) {
                            skylder += 150+etKlipning;
                        } else if (!line.contains("shampo") && line.contains("brush")) {
                            skylder += 100+etKlipning;
                        } else if (line.contains("shampo") && !line.contains("brush")) {
                          skylder += 50+etKlipning;
                    }
                    }

                }
            }

            System.out.println("I perioden fra " + startDateStr + " til " + endDateStr + " er der tjent: " + penge + "kr" + " og folk der skylder/credit: " + skylder);
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
public void logind() throws InterruptedException {
    Scanner scanner = new Scanner(System.in);
    boolean isharry = true;
    frisørloging harryskonto = new frisørloging("Harry","HarryHarry");
    frisørloging bruger1 = null;
    int value = 3;
    while(isharry){

        System.out.println("Username: ");
        String username = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();

        bruger1 = new frisørloging(username,password);
        if (bruger1.equals(harryskonto)) {
            System.out.println("welcome revisor");
            isharry = false;
            readOgAddUpEfter();
        }else{
            System.out.println("ikke en rigtig bruger, angiv en bruger du har nu " + (value-1) + " forsøg tilbage");
            value-=1;
        }if (value == 0){

            for (int i = 10; i >=0 ; i--) {
                Thread.sleep(1000);
                System.out.print(i  +"\r");

            }

            System.out.println("du kunne ikke logge ind");
            isharry = false;
        }


    }


}


}
