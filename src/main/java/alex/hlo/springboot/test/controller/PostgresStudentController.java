package alex.hlo.springboot.test.controller;

import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.exception.StudentException;
import alex.hlo.springboot.test.model.ErrorMessage;
import alex.hlo.springboot.test.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "PostgreSQL Student Controller", description = "PostgreSQL connect API")
@Api("PostgreSQL Student API")
@RequestMapping("postgres/student")
public class PostgresStudentController {

    private final StudentService studentService;

    public PostgresStudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "/get")
    @Tag(name = "PostgreSQL Student Controller")
    @ApiOperation(value = "Get student by last name", response = Student.class, produces = "application/json")
    public ResponseEntity<Student> getStudentByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(studentService.getStudentByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @Tag(name = "PostgreSQL Student Controller")
    @ApiOperation(value = "Get all students", response = List.class, produces = "application/json")
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    @Tag(name = "PostgreSQL Student Controller")
    @ApiOperation(value = "Save student", response = Student.class, produces = "application/json")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    @Tag(name = "PostgreSQL Student Controller")
    @ApiOperation(value = "Update student", response = Student.class, produces = "application/json")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.saveStudent(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    @Tag(name = "PostgreSQL Student Controller")
    @ApiOperation(value = "Delete student by id", response = Student.class, produces = "application/json")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ErrorMessage> handleException(StudentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
