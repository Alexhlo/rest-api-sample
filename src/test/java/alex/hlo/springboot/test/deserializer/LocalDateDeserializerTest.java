package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.deserializer.common.AbstractDeserializerTestHelper;
import alex.hlo.springboot.test.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateDeserializerTest extends AbstractDeserializerTestHelper<Student> {

    public LocalDateDeserializerTest() {
        super("birthDate", Student.class);
    }

    @Test
    void validSubjectValueTest() throws JsonProcessingException {
        assertNotNull(mapEntityFromJson("2022-03-21").getBirthDate());
    }

    @Test
    void ifSubjectIsNullTest() throws JsonProcessingException {
        assertNull(mapEntityFromJson( null).getBirthDate());
    }

    @Test
    void throwExceptionIfWrongSubjectPatternTest() {
        assertThrows(JsonMappingException.class, this::mapEntityFromJsonWithWrongValue);
    }

}