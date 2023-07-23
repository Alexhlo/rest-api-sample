package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.entity.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "semesters")
@Schema(name = "Semester", description = "Student semester list")
public class Semester extends BaseEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Schema(description = "Semester id", accessMode = READ_ONLY)
    private String id;

    @Schema(description = "Semester count")
    @Column(name = "semester_count", nullable = false)
    private Integer count;

    @Schema(description = "Semester complete")
    @Column(name = "semester_accepted", nullable = false)
    private Boolean accepted;

    public Semester(Integer count, Boolean accepted) {
        this.count = count;
        this.accepted = accepted;
    }
}
