package alex.hlo.springboot.test.entity;

import alex.hlo.springboot.test.deserializer.LocalDateDeserializer;
import alex.hlo.springboot.test.generator.StudentIdGenerator;
import alex.hlo.springboot.test.model.Gender;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity(name = "student")
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
    private Gender gender;

    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Integer age;

    @OneToMany(targetEntity = Subject.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Set<Subject> subjects;

    @OneToMany(targetEntity = Semester.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private Set<Semester> semesters;

}
