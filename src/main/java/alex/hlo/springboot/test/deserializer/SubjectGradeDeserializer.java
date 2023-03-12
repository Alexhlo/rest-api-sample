package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.exception.StudentServiceException;
import alex.hlo.springboot.test.model.enums.SubjectGrade;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class SubjectGradeDeserializer extends StdDeserializer<SubjectGrade> {

    public SubjectGradeDeserializer() {
        super(SubjectGradeDeserializer.class);
    }

    @Override
    public SubjectGrade deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        for (SubjectGrade grade : SubjectGrade.values()) {
            if (grade.name().equalsIgnoreCase(node.textValue())) {
                return SubjectGrade.valueOf(node.textValue().toUpperCase(Locale.ROOT));
            }
        }

        throw new StudentServiceException("Unexpected subject grade field value! Expected: " + Arrays.toString(SubjectGrade.values()));
    }
}
