package alex.hlo.springboot.test.entity.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    @CreationTimestamp
    @Schema(description = "create timestamp of record")
    private LocalDateTime created;

    @UpdateTimestamp
    @Schema(description = "update timestamp of record")
    private LocalDateTime updated;

}
