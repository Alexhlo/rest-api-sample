package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.deserializer.common.AbstractDeserializerTestHelper;
import alex.hlo.springboot.test.entity.Subject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubjectGradeDeserializerTest extends AbstractDeserializerTestHelper<Subject> {

    public SubjectGradeDeserializerTest() {
        super("grade", Subject.class);
    }

    @Test
    void validSubjectValueTest() throws JsonProcessingException {
        assertNotNull(mapEntityFromJson("A").getGrade());
    }

    @Test
    void ifSubjectIsNullTest() throws JsonProcessingException {
        assertNull(mapEntityFromJson( null).getGrade());
    }

    @Test
    void throwExceptionIfWrongSubjectPatternTest() {
        assertThrows(JsonMappingException.class, this::mapEntityFromJsonWithWrongValue);
    }
}