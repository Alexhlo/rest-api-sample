package alex.hlo.springboot.test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity(name = "semesters")
@AllArgsConstructor
@NoArgsConstructor
public class Semester {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(name = "semester_id", nullable = false, unique = true)
    private String id;

    @Column(name = "semester_count", nullable = false)
    private Integer count;

    @Column(name = "semester_accepted", nullable = false)
    private Boolean accepted;

    public Semester(Integer count, Boolean accepted) {
        this.count = count;
        this.accepted = accepted;
    }
}
