package alex.hlo.springboot.test;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Disabled("Need DB connections")
public class ApplicationContextBootTest {

    @Test
    void appContextBootTest() {}
}
