package openinghours;

import java.time.*;

public class Reservation {
    private String name;
    private LocalDateTime time;
    private String vare;
    private String betaling;
    public String toString() {
        return name + ";" + time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    public LocalDateTime getTime() {
        return time;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    //det nye jeg har added
    public void setVare(String vare) {
        this.vare = vare;
    }

    public void setBetaling(String betaling) {
        this.betaling = betaling;
    }
}
