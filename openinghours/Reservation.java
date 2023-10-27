package openinghours;

import java.time.*;

public class Reservation {
    private String name;
    private LocalDateTime time;
    
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

}
