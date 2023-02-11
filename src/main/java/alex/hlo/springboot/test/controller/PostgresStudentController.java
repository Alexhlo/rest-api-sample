package alex.hlo.springboot.test.controller;

import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@Api(tags = { "Student API" })
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Something wrong"),
        @ApiResponse(code = 404, message = "Student wasn't found"),
        @ApiResponse(code = 500, message = "Service error"),
})
@RequestMapping(value = "postgres/students", produces = "application/json")
public class PostgresStudentController {

    private final StudentService studentService;

    public PostgresStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/get/{id}")
    @ApiOperation(value = "Get student by id", response = Student.class)
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        return new ResponseEntity<>(studentService.getStudentById(id), OK);
    }

    @GetMapping(value = "/get/lastName/{lastName}")
    @ApiOperation(value = "Get students by last name", response = List.class)
    public ResponseEntity<List<Student>> getStudentByLastName(@PathVariable String lastName) {
        return new ResponseEntity<>(studentService.getStudentsByLastName(lastName), OK);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "Get all students", response = List.class)
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), OK);
    }

    @PostMapping(value = "/get")
    @ApiOperation(value = "Get students by ids", response = List.class)
    public ResponseEntity<List<Student>> getStudentByIds(@RequestBody List<String> ids) {
        return new ResponseEntity<>(studentService.getStudentsById(ids), OK);
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "Save student", response = Student.class)
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), OK);
    }

    @PostMapping(value = "/save/all")
    @ApiOperation(value = "Save all students", response = List.class)
    public ResponseEntity<List<Student>> saveStudent(@RequestBody List<Student> students) {
        return new ResponseEntity<>(studentService.saveAllStudents(students), OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ApiOperation(value = "Delete student by id")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudentById(id);
    }

}
