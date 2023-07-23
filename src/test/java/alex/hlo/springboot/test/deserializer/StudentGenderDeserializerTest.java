package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.deserializer.common.AbstractDeserializerTestHelper;
import alex.hlo.springboot.test.entity.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentGenderDeserializerTest extends AbstractDeserializerTestHelper<Student> {

    public StudentGenderDeserializerTest() {
        super("gender", Student.class);
    }

    @Test
    void validSubjectValueTest() throws JsonProcessingException {
        assertNotNull(mapEntityFromJson("MALE").getGender());
    }

    @Test
    void ifSubjectIsNullTest() throws JsonProcessingException {
        assertNull(mapEntityFromJson( null).getGender());
    }

    @Test
    void throwExceptionIfWrongSubjectPatternTest() {
        assertThrows(JsonMappingException.class, this::mapEntityFromJsonWithWrongValue);
    }

}