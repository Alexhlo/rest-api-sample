package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.model.enums.Gender;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class GenderDeserializer extends JsonDeserializer<Gender> {

    @Override
    public Gender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(node.textValue())) {
                return Gender.valueOf(node.textValue().toUpperCase(Locale.ROOT));
            }
        }

        throw JsonMappingException.from(jsonParser, "Unexpected gender field value! Expected: " + Arrays.toString(Gender.values()));
    }
}
