package Utilitati;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class OurTimestamp {
    private Timestamp timestamp;

    public OurTimestamp(){}

    public Long getMillis(){
        timestamp = Timestamp.from(Instant.now());
        return timestamp.getTime();
    }
}
