package alex.hlo.springboot.test.service.impl;


import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.exception.StudentException;
import alex.hlo.springboot.test.repository.StudentRepository;
import alex.hlo.springboot.test.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static alex.hlo.springboot.test.utils.DateUtil.getAgeFromDate;
import static java.util.Objects.isNull;
import static org.springframework.util.ObjectUtils.*;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    private final Set<String> nullableFields = Set.of("id", "middleName", "age");

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student saveStudent(Student student) {
        if (isNull(student)) throw new StudentException("Student does not exist!");

        log.info("StudentService.saveStudent = " + student);

        validateStudent(student);
        fillStudentStudentAge(student);

        if (isEmpty(student.getId())) {
            return studentRepository.save(student);
        } else {
            Student studentById = studentRepository.getById(student.getId());
            return studentRepository.save(studentById);
        }
    }

    @Override
    @Transactional(isolation =  Isolation.READ_COMMITTED)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentByLastName(String lastName) {
        if (isEmpty(lastName)) throw new StudentException("Students last name is empty.");

        Student student = studentRepository.findStudentByLastName(lastName);

        if (isNull(student)) throw new StudentException("No student with last name: " + lastName);

        log.info("StudentService.getStudentByLastName = " + student);

        return student;
    }

    @Override
    public void deleteStudentById(String id) {
        if (isEmpty(id)) throw new StudentException("Student id is empty.");

        log.info("StudentService.deleteStudentById = " + id);

        studentRepository.deleteById(id);
    }

    private void validateStudent(Student student) {
        Set<String> emptyFields = new HashSet<>();

        Field[] fields = student.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(student);
                if (isEmpty(fieldValue) && !nullableFields.contains(field.getName())) {
                    emptyFields.add(field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        if (emptyFields.size() != 0) {
            throw new StudentException("Student has no required fields: " + emptyFields);
        }
    }

    private void fillStudentStudentAge(Student student) {
        student.setAge(getAgeFromDate(student.getBirthDate()));
    }

}
