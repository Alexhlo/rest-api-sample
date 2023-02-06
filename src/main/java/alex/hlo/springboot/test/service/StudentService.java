package alex.hlo.springboot.test.service;

import alex.hlo.springboot.test.entity.Student;

import java.util.List;

public interface StudentService {

    Student getStudentById(String id);
    List<Student> getStudentsByLastName(String lastName);
    List<Student> getStudentsById(List<String> ids);
    List<Student> getAllStudents();

    Student saveStudent(Student student);
    List<Student> saveAllStudents(List<Student> students);

    void deleteStudentById(String id);

}
