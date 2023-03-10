package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.exception.StudentServiceException;
import alex.hlo.springboot.test.model.enums.Gender;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class GenderDeserializer extends StdDeserializer<Gender> {

    public GenderDeserializer() {
        super(GenderDeserializer.class);
    }

    @Override
    public Gender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(node.textValue())) {
                return Gender.valueOf(node.textValue().toUpperCase(Locale.ROOT));
            }
        }

        throw new StudentServiceException("Unexpected gender field value! Expected: " + Arrays.toString(Gender.values()));
    }
}
