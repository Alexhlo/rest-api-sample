package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.model.enums.SubjectGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "subjects")
@Schema(name = "Subject", description = "Student subject list")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id", nullable = false, unique = true)
    @Schema(description = "Subject id", accessMode = READ_ONLY)
    private long id;

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
