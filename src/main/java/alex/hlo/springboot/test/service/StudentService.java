package alex.hlo.springboot.test.service;

import alex.hlo.springboot.test.entity.Student;

import java.util.List;

public interface StudentService {

    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentByLastName(String lastName);

    void deleteStudentById(String id);

}
