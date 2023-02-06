package alex.hlo.springboot.test;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    void testRandom() {
        String s = new RandomString(8).nextString();
        System.out.println(s);
    }
}
