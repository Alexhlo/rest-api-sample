package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.utils.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (Objects.nonNull(node)) {
            try {
                return DateUtil.stringToLocalDate(node.textValue());
            } catch (DateTimeParseException ignored) { }
        }

        throw JsonMappingException.from(jsonParser, "Input date must be in pattern: yyyy-MM-dd");
    }
}
