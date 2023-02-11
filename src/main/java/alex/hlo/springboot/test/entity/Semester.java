package alex.hlo.springboot.test.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "semesters")
@ApiModel(value = "Semester", description = "Student semester list")
public class Semester {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "semester_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @ApiModelProperty(name = "Semester id", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    private String id;

    @ApiModelProperty(name = "Semester count")
    @Column(name = "semester_count", nullable = false)
    private Integer count;

    @ApiModelProperty(name = "Semester complete")
    @Column(name = "semester_accepted", nullable = false)
    private Boolean accepted;

    public Semester(Integer count, Boolean accepted) {
        this.count = count;
        this.accepted = accepted;
    }
}
