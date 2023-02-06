package alex.hlo.springboot.test;

import alex.hlo.springboot.test.entity.Semester;
import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.entity.Subject;
import alex.hlo.springboot.test.exception.StudentNotFoundException;
import alex.hlo.springboot.test.model.enums.Gender;
import alex.hlo.springboot.test.model.enums.SubjectGrade;
import alex.hlo.springboot.test.service.StudentService;
import alex.hlo.springboot.test.utils.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static java.util.Objects.requireNonNull;

@SpringBootTest
@ActiveProfiles("test")
@Disabled("Need DB connections")
public class PostgresStudentsControllerTest {

    @Autowired
    private StudentService studentService;

    @Test
    @Order(1)
    void saveStudentAndCheckToDbTest() {
        Student saveStudent = studentService.saveStudent(generateSimpleStudentModel(0));

        Student studentById = studentService.getStudentById(saveStudent.getId());

        Assertions.assertEquals(saveStudent, studentById);
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
    @Disabled("Optional test, too much resources use")
    void getAllStudentsTest() {
        List<Student> allStudents = studentService.getAllStudents();

        Assertions.assertTrue(allStudents.size() > 0);
    }

    private Student generateSimpleStudentModel(long number) {
        return new Student(
                "FirstName" + number,
                "LastName" + number,
                "MiddleName" + number,
                Gender.MALE,
                requireNonNull(DateUtil.stringToLocalDate("1975-05-05")).plusYears(number),
                initSubjects(),
                initSemesters()
        );
    }

    private Set<Subject> initSubjects() {
        return Set.of(
                new Subject("History", getRandomGrade()),
                new Subject("Math", getRandomGrade())
        );
    }

    private static Set<Semester> initSemesters() {
        return Set.of(
                new Semester(1, true),
                new Semester(2, false)
        );
    }

    private SubjectGrade getRandomGrade() {
        SubjectGrade[] grades = SubjectGrade.values();
        int randomIndex = new Random().nextInt(grades.length - 1);
        return grades[randomIndex];
    }

}
