package alex.hlo.springboot.test.model.enums;

import alex.hlo.springboot.test.deserializer.SubjectGradeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = SubjectGradeDeserializer.class)
public enum SubjectGrade {
    A,
    B,
    C,
    D,
    F
}
