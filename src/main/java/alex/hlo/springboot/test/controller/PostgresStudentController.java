package alex.hlo.springboot.test.controller;

import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@Tag(name = "Student API" )
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Something wrong"),
        @ApiResponse(responseCode = "404", description = "Student wasn't found"),
        @ApiResponse(responseCode = "500", description = "Service error"),
})
@RequestMapping(value = "postgres/students", produces = "application/json")
public class PostgresStudentController {

    private final StudentService studentService;

    public PostgresStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/get/{id}")
    @Operation(summary = "Get student by id")
    public ResponseEntity<Student> getStudentById(@PathVariable String id) {
        return new ResponseEntity<>(studentService.getStudentById(id), OK);
    }

    @Operation(summary = "Get students by last name")
    @GetMapping(value = "/get/lastName/{lastName}")
    public ResponseEntity<List<Student>> getStudentByLastName(@PathVariable String lastName) {
        return new ResponseEntity<>(studentService.getStudentsByLastName(lastName), OK);
    }

    @GetMapping("/get/all")
    @Operation(summary = "Get all students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), OK);
    }

    @PostMapping(value = "/get")
    @Operation(summary = "Get students by ids")
    public ResponseEntity<List<Student>> getStudentByIds(@RequestBody List<String> ids) {
        return new ResponseEntity<>(studentService.getStudentsById(ids), OK);
    }

    @PostMapping(value = "/save")
    @Operation(summary = "Save student")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), OK);
    }

    @PostMapping(value = "/save/all")
    @Operation(summary = "Save all students")
    public ResponseEntity<List<Student>> saveStudent(@RequestBody List<Student> students) {
        return new ResponseEntity<>(studentService.saveAllStudents(students), OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Delete student by id")
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudentById(id);
    }

}
