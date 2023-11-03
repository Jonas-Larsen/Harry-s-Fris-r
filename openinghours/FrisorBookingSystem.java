package openinghours;

import java.util.*;
import java.io.File;
import java.time.*;

public class FrisorBookingSystem {
    private List<Date> reserveredeTider;


    public FrisorBookingSystem(){
        reserveredeTider = new ArrayList<>();
    }

    public ArrayList<LocalTime> findLedigeTider(LocalDate day){
        var ledigeTider = new ArrayList<LocalTime>();

        int i = 10;
        while (i != 18) {
            ledigeTider.add(LocalTime.of(i, 0));
            ledigeTider.add(LocalTime.of(i, 30));
            i++;
        }

        try {
            Scanner file = new Scanner(new File("ReservationList.csv"));
            while (file.hasNextLine()) {
                Scanner currentLine = new Scanner (file.nextLine());
                currentLine.useDelimiter(";");
                currentLine.next();
                LocalDateTime dateTime = LocalDateTime.parse(currentLine.next());
                LocalDate date = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth());
                if (date.isEqual(day)) {
                    LocalTime time = LocalTime.of(dateTime.getHour(), dateTime.getMinute());
                    ledigeTider.remove(ledigeTider.indexOf(time));
                }
            
            }
        } catch (Exception e) {}
        
        return ledigeTider;
    }

    private boolean  erTidspunktReserveret(Date tid, int antalMinutterMellemAftaler){
        for (Date reserveretTid : reserveredeTider){
            if (tid.after(reserveretTid) && tid.before(tilføjMinutter(reserveretTid, antalMinutterMellemAftaler))){
                return true;
            }
        }
        return false;
    }

    private Date setTidspunkt(Date dato, int time, int minut){
        Date nyDato = new Date(dato.getTime());
        nyDato.setHours(time);
        nyDato.setMinutes(minut);
        return nyDato;
    }

    private Date tilføjMinutter(Date dato, int antalMinutter){
        long tid = dato.getTime();
        tid += antalMinutter * 60 * 1000;
        return new Date(tid);
    }
    
    private boolean isTimeSlotReserved(Date time, int durationMinutes) {
        for (Date reservedTime : reserveredeTider) {
            Date endTime = addMinutes(reservedTime, durationMinutes);
            if (time.after(reservedTime) && time.before(endTime)) {
                return true;
            }
        }
        return false;
    }

    private Date setHourAndMinute(Date date, int hour, int minute) {
        Date newDate = new Date(date.getTime());
        newDate.setHours(hour);
        newDate.setMinutes(minute);
        return newDate;
    }

    private Date addMinutes(Date date, int minutes) {
        long time = date.getTime();
        time += minutes * 60 * 1000;
        return new Date(time);
    }

}

         
