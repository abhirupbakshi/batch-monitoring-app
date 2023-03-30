package example.app.utility;

import example.app.exceptions.NullArgumentException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class CheckDate {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    public static LocalDate isValidAndConvert(String date) throws NullArgumentException, DateTimeParseException {
        if(date == null)
            throw new NullArgumentException();

        return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}
