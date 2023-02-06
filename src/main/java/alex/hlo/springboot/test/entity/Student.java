package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.deserializer.GenderDeserializer;
import alex.hlo.springboot.test.deserializer.LocalDateDeserializer;
import alex.hlo.springboot.test.generator.StudentIdGenerator;
import alex.hlo.springboot.test.model.enums.Gender;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity(name = "students")
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GenericGenerator(name = StudentIdGenerator.NAME, strategy = "alex.hlo.springboot.test.generator.StudentIdGenerator")
    @GeneratedValue(generator = StudentIdGenerator.NAME)
    @Column(name = "student_id", nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String middleName;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = GenderDeserializer.class)
    private Gender gender;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDate;
    private Integer age;

    @OneToMany(targetEntity = Subject.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Set<Subject> subjects;

    @OneToMany(targetEntity = Semester.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
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
