package alex.hlo.springboot.test.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public class DateUtilTest {

    @Test
    void localDateToStringTest() {
        LocalDate now = LocalDate.now();

        String result = DateUtil.localDateToString(now);

        Assertions.assertEquals(String.class, requireNonNull(result).getClass());
    }

    @Test
    void stringToLocalDateTest() {
        String date = "2023-01-31";

        LocalDate result = DateUtil.stringToLocalDate(date);

        Assertions.assertEquals(LocalDate.class, requireNonNull(result).getClass());
    }

    @Test
    void getAgeFromDateTest() {
        String birthDate = "1990-06-28";

        LocalDate now = LocalDate.now();
        LocalDate stringToLocalDate = DateUtil.stringToLocalDate(birthDate);

        requireNonNull(stringToLocalDate);

        int result = DateUtil.getAgeFromDate(stringToLocalDate);
        int calculatedAge = now.getYear() - stringToLocalDate.getYear();

        Assertions.assertEquals(result, calculatedAge);
    }
}
