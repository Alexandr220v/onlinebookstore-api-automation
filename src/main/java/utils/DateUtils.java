package utils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DateUtils {
    public static String getCurrentDate() {
        return Instant.now().truncatedTo(ChronoUnit.SECONDS).toString();
    }
}
