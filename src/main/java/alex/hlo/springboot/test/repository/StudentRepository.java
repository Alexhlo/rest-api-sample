package alex.hlo.springboot.test.repository;

import alex.hlo.springboot.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentRepository extends JpaRepository<Student, String> {

    Student findStudentById(String id);

    List<Student> findAllByLastName(String lastName);

}
