package alex.hlo.springboot.test.utils;

import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class DateUtil {

    private static final String ISO_8601_PATTERN = "yyyy-MM-dd";
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern(ISO_8601_PATTERN).withZone(ZoneId.systemDefault());

    private DateUtil() {}

    public static String localDateToString(LocalDate date) {
        if (ObjectUtils.isEmpty(date)) return null;

        return date.format(DTF);
    }

    public static LocalDate stringToLocalDate(String date) {
        if (ObjectUtils.isEmpty(date)) return null;

        return LocalDate.parse(date, DTF);
    }

    public static int getAgeFromDate(LocalDate date) {
        if (ObjectUtils.isEmpty(date)) return 0;

        LocalDate now = LocalDate.now();

        return now.getYear() - date.getYear();
    }
}
