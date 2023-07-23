package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.deserializer.GenderDeserializer;
import alex.hlo.springboot.test.deserializer.LocalDateDeserializer;
import alex.hlo.springboot.test.entity.common.BaseEntity;
import alex.hlo.springboot.test.generator.StudentIdGenerator;
import alex.hlo.springboot.test.model.enums.Gender;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "students")
@Schema(name = "Student", description = "Student subject list")
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(generator = StudentIdGenerator.NAME)
    @Schema(description = "Student id", accessMode = READ_ONLY)
    @GenericGenerator(name = StudentIdGenerator.NAME, strategy = "alex.hlo.springboot.test.generator.StudentIdGenerator")
    private String id;

    @Column(nullable = false)
    @Schema(description = "Student first name")
    private String firstName;

    @Column(nullable = false)
    @Schema(description = "Student last name")
    private String lastName;

    @Schema(description = "Student middle name")
    private String middleName;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = GenderDeserializer.class)
    @Schema(description = "Student gender", example = "male, female")
    private Gender gender;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @Schema(description = "Student birthDate", example = "1990-10-10")
    private LocalDate birthDate;

    @Schema(description = "Student age")
    private Integer age;

    @Schema(description = "Student subject list")
    @JoinColumn(name = "student_id")
    @OneToMany(targetEntity = Subject.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Subject> subjects;

    @Schema(description = "Student semester list")
    @JoinColumn(name = "student_id")
    @OneToMany(targetEntity = Semester.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Semester> semesters;

    public Student(String firstName, String lastName, String middleName, Gender gender, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public Student(String firstName, String lastName, String middleName, Gender gender, LocalDate birthDate,
                   Set<Subject> subjects, Set<Semester> semesters) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.subjects = subjects;
        this.semesters = semesters;
    }
}
