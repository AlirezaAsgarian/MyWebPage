package util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateGetter {
    static DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss    ns");
    public static String getCurrentDate(){
        return myFormatObj.format(LocalDateTime.now());
    }
}
