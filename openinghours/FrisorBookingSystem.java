package openinghours;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FrisorBookingSystem {
    private List<Date> reserveredeTider;


    public FrisorBookingSystem(){
        reserveredeTider = new ArrayList<>();
    }

    public List<Date> findLedigeTider(Date dato, int antalMinutterMellemAftaler){
    List<Date> ledigeTider = new ArrayList<>();

    Date startTid = setTidspunkt(dato, 10, 0);
    Date slutTid = setTidspunkt(dato, 18, 0);

    Date nuværendeTid = startTid;

    while (nuværendeTid.before(slutTid)) {
        if (!erTidspunktReserveret(nuværendeTid, antalMinutterMellemAftaler)) {
            ledigeTider.add(nuværendeTid);
        }
        nuværendeTid = tilføjMinutter(nuværendeTid, antalMinutterMellemAftaler);
    }
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

         
