package alex.hlo.springboot.test.deserializer.common;

import alex.hlo.springboot.test.entity.common.BaseEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

import java.util.Objects;

@Setter
public abstract class AbstractDeserializerTestHelper<T extends BaseEntity> {

    private final ObjectMapper mapper = new ObjectMapper();
    private String fieldName;
    private Class<T> entityClass;

    public AbstractDeserializerTestHelper(String fieldName, Class<T> entityClass) {
        this.fieldName = fieldName;
        this.entityClass = entityClass;
    }

    public void mapEntityFromJsonWithWrongValue() throws JsonProcessingException {
        mapEntityFromJson("wrongValue");
    }

    public T mapEntityFromJson(String fieldValue) throws JsonProcessingException {
        String json = getEntityJson(fieldValue);

        return mapper.readValue(json, this.entityClass);
    }

    private String getEntityJson(String fieldValue) {
        String str = Objects.nonNull(fieldValue) ? "\"" + fieldValue + "\"" : null;

        return String.format("""
                {
                    "%s": %s
                }
                """, this.fieldName, str);
    }
}
