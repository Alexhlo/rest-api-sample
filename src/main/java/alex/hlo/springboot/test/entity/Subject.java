package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.model.enums.SubjectGrade;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "subjects")
@ApiModel(value = "Subject", description = "Student subject list")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id", nullable = false, unique = true)
    @ApiModelProperty(name = "Subject id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private long id;

    @ApiModelProperty(name = "Subject name")
    @Column(name="subject_name", length=50, nullable=false)
    private String name;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(name = "Subject grade", example = "A, B, C")
    private SubjectGrade grade;

    public Subject(String name, SubjectGrade grade) {
        this.name = name;
        this.grade = grade;
    }
}
