package alex.hlo.springboot.test.repository;

import alex.hlo.springboot.test.entity.Student;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    Student findStudentById(String id);

    List<Student> findAllByLastName(String lastName);

    @Timed("studentRepositoryFindAllTime")
    @Counted("studentRepositoryFindAllCount")
    List<Student> findAll();
}
