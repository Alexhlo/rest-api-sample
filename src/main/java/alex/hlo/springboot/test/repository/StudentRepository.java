package alex.hlo.springboot.test.repository;

import alex.hlo.springboot.test.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface StudentRepository extends JpaRepository<Student, String> {

    Student findStudentByLastName(String lastName);

}
