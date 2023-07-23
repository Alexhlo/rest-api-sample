package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.model.enums.SubjectGrade;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class SubjectGradeDeserializer extends JsonDeserializer<SubjectGrade> {

    @Override
    public SubjectGrade deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        for (SubjectGrade grade : SubjectGrade.values()) {
            if (grade.name().equalsIgnoreCase(node.textValue())) {
                return SubjectGrade.valueOf(node.textValue().toUpperCase(Locale.ROOT));
            }
        }

        throw JsonMappingException.from(jsonParser, "Unexpected subject grade field value! Expected: " + Arrays.toString(SubjectGrade.values()));
    }
}
