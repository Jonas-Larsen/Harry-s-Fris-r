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
}
         