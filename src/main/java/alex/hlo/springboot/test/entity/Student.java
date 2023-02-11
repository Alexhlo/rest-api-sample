package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.deserializer.GenderDeserializer;
import alex.hlo.springboot.test.deserializer.LocalDateDeserializer;
import alex.hlo.springboot.test.generator.StudentIdGenerator;
import alex.hlo.springboot.test.model.enums.Gender;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "students")
@ApiModel(value = "Student", description = "Student subject list")
public class Student {

    @Id
    @GeneratedValue(generator = StudentIdGenerator.NAME)
    @Column(name = "student_id", nullable = false, unique = true)
    @ApiModelProperty(name = "Student id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @GenericGenerator(name = StudentIdGenerator.NAME, strategy = "alex.hlo.springboot.test.generator.StudentIdGenerator")
    private String id;

    @Column(nullable = false)
    @ApiModelProperty(name = "Student first name")
    private String firstName;

    @Column(nullable = false)
    @ApiModelProperty(name = "Student last name")
    private String lastName;

    @ApiModelProperty(name = "Student middle name")
    private String middleName;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = GenderDeserializer.class)
    @ApiModelProperty(name = "Student gender", example = "male, female")
    private Gender gender;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @ApiModelProperty(name = "Student birthDate", example = "1990-10-10")
    private LocalDate birthDate;

    @ApiModelProperty(name = "Student age")
    private Integer age;

    @ApiModelProperty(name = "Student subject list")
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @OneToMany(targetEntity = Subject.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Subject> subjects;

    @ApiModelProperty(name = "Student semester list")
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @OneToMany(targetEntity = Semester.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Semester> semesters;

    public Student(String firstName, String lastName, String middleName, Gender gender, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public Student(String firstName, String lastName, String middleName, Gender gender, LocalDate birthDate, Set<Subject> subjects, Set<Semester> semesters) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.subjects = subjects;
        this.semesters = semesters;
    }
}
