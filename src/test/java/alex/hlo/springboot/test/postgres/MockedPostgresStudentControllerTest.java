package alex.hlo.springboot.test.postgres;

import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.exception.StudentNotFoundException;
import alex.hlo.springboot.test.service.StudentService;
import alex.hlo.springboot.test.utils.StudentUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MockedPostgresStudentControllerTest {
    private static final String STUDENT_ID = "ABCxyzXd";
    private static final String STUDENT_LAST_NAME = "LastName0";

    private static StudentService studentService;
    private static Student generatedStudent;

    @BeforeAll
    public static void initMocks() {
        studentService = Mockito.mock(StudentService.class);

        generatedStudent = StudentUtil.generateSimpleStudentModel(0);
        generatedStudent.setId(STUDENT_ID);

        List<Student> generatedStudentList = List.of(generatedStudent);

        Mockito
                .when(studentService.saveStudent(generatedStudent))
                .thenReturn(generatedStudent);

        Mockito
                .when(studentService.getStudentById(STUDENT_ID))
                .thenReturn(generatedStudent);

        Mockito
                .when(studentService.getStudentsByLastName(STUDENT_LAST_NAME))
                .thenReturn(generatedStudentList);

        Mockito
                .doNothing()
                .when(studentService).deleteStudentById(STUDENT_ID);

        Mockito
                .when(studentService.saveAllStudents(List.of()))
                .thenReturn(generatedStudentList);

        Mockito
                .when(studentService.getAllStudents())
                .thenReturn(generatedStudentList);
    }

    @Test
    @Order(1)
    void mockedSaveStudentAndCheckToDbTest() {
        Student mockedSavedStudent = studentService.saveStudent(generatedStudent);

        Student mockedStudent= studentService.getStudentById(mockedSavedStudent.getId());

        Assertions.assertEquals(mockedSavedStudent, mockedStudent);
    }

    @Test
    @Order(2)
    void mockedGetStudentByLastNameTest() {
        List<Student> students = studentService.getStudentsByLastName(STUDENT_LAST_NAME);

        Assertions.assertTrue(students.size() > 0);
    }

    @Test
    @Order(3)
    void mockedDeleteStudentByIdTest() {
        studentService.deleteStudentById(STUDENT_ID);

        Mockito
                .when(studentService.getStudentById(STUDENT_ID))
                .thenThrow(StudentNotFoundException.class);

        Assertions.assertThrows(StudentNotFoundException.class,
                () -> studentService.getStudentById(STUDENT_ID));
    }

    @Test
    @Order(4)
    void mockedGenerateStudentsAndSaveAllTest() {
        List<Student> savedStudents = studentService.saveAllStudents(List.of());

        Assertions.assertEquals(savedStudents.size(), 1);
    }

    @Test
    @Order(5)
    void mockedGetAllStudentsTest() {
        List<Student> allStudents = studentService.getAllStudents();

        Assertions.assertTrue(allStudents.size() > 0);
    }
}
