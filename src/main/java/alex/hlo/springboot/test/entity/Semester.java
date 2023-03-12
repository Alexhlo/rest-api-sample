package alex.hlo.springboot.test.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "semesters")
@Schema(name = "Semester", description = "Student semester list")
public class Semester {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "semester_id", nullable = false, unique = true)
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
