package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.entity.common.BaseEntity;
import alex.hlo.springboot.test.model.enums.SubjectGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "subjects")
@Schema(name = "Subject", description = "Student subject list")
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Subject id", accessMode = READ_ONLY)
    private Long id;

    @Schema(description = "Subject name")
    @Column(name="subject_name", length=50, nullable=false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Subject grade", example = "A, B, C")
    private SubjectGrade grade;

    public Subject(String name, SubjectGrade grade) {
        this.name = name;
        this.grade = grade;
    }
}
