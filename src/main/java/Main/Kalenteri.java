// Tätä käytetään timestamppien lisäämiseen


package Main;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

public class Kalenteri {
    
    private Calendar calendar;
    
    public Kalenteri() {
        this.calendar = Calendar.getInstance();
    }
    
    public Timestamp getTime() {
        Date now = calendar.getTime();
        return new Timestamp(now.getTime());
    }
    
}
