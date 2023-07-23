package alex.hlo.springboot.test.postgres;

import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.exception.NotFoundException;
import alex.hlo.springboot.test.model.enums.Gender;
import alex.hlo.springboot.test.service.student.StudentService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static alex.hlo.springboot.test.utils.TestStudentUtil.generateSimpleStudentModel;
import static alex.hlo.springboot.test.utils.TestStudentUtil.generateSimpleStudentModelList;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostgresStudentsControllerTest {

    @Autowired
    private StudentService studentService;

    private static String studentId;
    private static Student student;

    @Test
    @Order(1)
    void saveStudentAndCheckInDbTest() {
        studentId = studentService
                .saveStudent(generateSimpleStudentModel(0))
                .getId();

        Assertions.assertTrue(Objects.nonNull(studentId));
    }

    @Test
    @Order(2)
    void getStudentByIdTest() {
        student = studentService.getStudentById(studentId);

        Assertions.assertTrue(Objects.nonNull(student));
    }

    @Test
    @Order(3)
    void updateCreatedStudentById() {
        student.setGender(Gender.FEMALE);

        Student updatedStudent = studentService.saveStudent(student);

        Assertions.assertEquals(updatedStudent.getId(), student.getId());
        Assertions.assertEquals(updatedStudent.getGender(), student.getGender());
    }

    @Test
    @Order(4)
    void getStudentsByLastNameTest() {
        List<Student> students = studentService.getStudentsByLastName("LastName0");

        Assertions.assertTrue(students.size() > 0);
    }

    @Test
    @Order(5)
    void deleteStudentByIdTest() {
        studentService.deleteStudentById(studentId);

        Assertions.assertThrows(NotFoundException.class, () -> studentService.getStudentById(studentId));
    }

    @Test
    @Order(6)
    void generateStudentsAndSaveAllTest() {
        int count = 10_000;

        List<Student> students = generateSimpleStudentModelList(count);
        List<Student> savedStudents = studentService.saveAllStudents(students);

        Assertions.assertEquals(savedStudents.size(), count);
    }

    @Test
    @Order(7)
    void getAllStudentsTest() {
        List<Student> allStudents = studentService.getAllStudents();

        Assertions.assertTrue(allStudents.size() > 0);
    }

    @Test
    @Order(8)
    @Sql(scripts = "/sql/drop_all_tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void dropDbTablesTest() { }
}
