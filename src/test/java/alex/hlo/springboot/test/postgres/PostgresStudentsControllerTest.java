package alex.hlo.springboot.test.postgres;

import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.exception.StudentNotFoundException;
import alex.hlo.springboot.test.service.StudentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static alex.hlo.springboot.test.utils.TestStudentUtil.generateSimpleStudentModel;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostgresStudentsControllerTest {

    @Autowired
    private StudentService studentService;

    @Test
    @Order(1)
    void saveStudentAndCheckToDbTest() {
        Student savedStudent = studentService.saveStudent(generateSimpleStudentModel(0));

        Student student = studentService.getStudentById(savedStudent.getId());

        Assertions.assertEquals(savedStudent, student);
    }

    @Test
    @Order(2)
    void getStudentsByLastNameTest() {
        List<Student> students = studentService.getStudentsByLastName("LastName0");

        Assertions.assertTrue(students.size() > 0);
    }

    @Test
    @Order(3)
    void deleteStudentByIdTest() {
        Student student = studentService.saveStudent(generateSimpleStudentModel(1));

        studentService.deleteStudentById(student.getId());

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(student.getId()));
    }

    @Test
    @Order(4)
    void generateStudentsAndSaveAllTest() {
        List<Student> students = new ArrayList<>();

        int count = 10;

        for (int i = 0; i < count; i++) {
            students.add(generateSimpleStudentModel(i));
        }

        List<Student> savedStudents = studentService.saveAllStudents(students);

        Assertions.assertEquals(savedStudents.size(), count);
    }

    @Test
    @Order(5)
    void getAllStudentsTest() {
        List<Student> allStudents = studentService.getAllStudents();

        Assertions.assertTrue(allStudents.size() > 0);
    }

    @Test
    @Order(6)
    @Sql(scripts = "/sql/drop_all_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void dropDbTablesTest() { }
}
