package alex.hlo.springboot.test.model.enums;

import alex.hlo.springboot.test.deserializer.GenderDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = GenderDeserializer.class)
public enum Gender {
    MALE,
    FEMALE
}
