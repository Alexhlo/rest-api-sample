package alex.hlo.springboot.test.deserializer;

import alex.hlo.springboot.test.exception.StudentException;
import alex.hlo.springboot.test.utils.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

    public LocalDateDeserializer() {
        super(LocalDateDeserializer.class);
    }

    protected LocalDateDeserializer(Class<?> vc) {
        super(vc);
    }

    protected LocalDateDeserializer(JavaType valueType) {
        super(valueType);
    }

    protected LocalDateDeserializer(StdDeserializer<?> src) {
        super(src);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (Objects.nonNull(node)) {
            try {
                return DateUtil.parseDate(node.textValue());
            } catch (DateTimeParseException ignored) { }
        }

        throw new StudentException("Input date must be in pattern: yyyy-MM-dd");
    }
}
