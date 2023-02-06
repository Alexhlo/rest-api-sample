package alex.hlo.springboot.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "subjects")
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id", nullable = false, unique = true)
    private long id;

    @Column(name="subject_name", length=50, nullable=false)
    private String name;

    @Column(name="subject_grade", length=2, nullable=false)
    private String grade;

}