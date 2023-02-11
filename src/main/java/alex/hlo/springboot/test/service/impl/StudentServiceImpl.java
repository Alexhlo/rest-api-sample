package alex.hlo.springboot.test.service.impl;


import alex.hlo.springboot.test.entity.Semester;
import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.entity.Subject;
import alex.hlo.springboot.test.exception.StudentApiException;
import alex.hlo.springboot.test.exception.StudentNotFoundException;
import alex.hlo.springboot.test.exception.StudentServiceException;
import alex.hlo.springboot.test.repository.StudentRepository;
import alex.hlo.springboot.test.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static alex.hlo.springboot.test.utils.DateUtil.getAgeFromDate;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.ObjectUtils.isEmpty;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentById(String id) {
        if (isEmpty(id)) throw new StudentApiException("Students id must not be blank!");

        Student student = studentRepository.findStudentById(id);

        if (isNull(student)) throw new StudentNotFoundException("No student with id: " + id);

        log.info("StudentService.getStudentById = " + student);

        return student;
    }

    @Override
    public List<Student> getStudentsById(List<String> ids) {
        if (isEmpty(ids)) throw new StudentApiException("Students id list is empty.");

        List<Student> student = studentRepository.findAllById(ids);

        if (isEmpty(student)) throw new StudentNotFoundException("No students with ids: " + ids);

        log.info("StudentService.getStudentById = " + student);

        return student;
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        if (isEmpty(lastName)) throw new StudentApiException("Students must not be blank!");

        List<Student> students = studentRepository.findAllByLastName(lastName);

        if (isEmpty(students)) throw new StudentNotFoundException("No students with last name: " + lastName);

        log.info("StudentService.getStudentByLastName = " + students);

        return students;
    }

    @Override
    @Transactional(isolation =  Isolation.READ_COMMITTED)
    public List<Student> getAllStudents() {
        List<Student> all = studentRepository.findAll();

        if (isEmpty(all)) throw new StudentNotFoundException("Students not found!");

        return all;
    }

    @Override
    public Student saveStudent(Student student) {
        if (isNull(student)) throw new StudentApiException("Student does not be null!");

        log.info("StudentService.saveStudent = " + student);

        validateStudent(student);
        fillStudentStudentAge(student);

        return studentRepository.save(student);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<Student> saveAllStudents(List<Student> students) {
        if (isEmpty(students)) throw new StudentApiException("Students does not exits!");

        log.info("StudentService.saveAllStudents = " + students);

        students.forEach(student -> {
            validateStudent(student);
            fillStudentStudentAge(student);
        });

        return studentRepository.saveAll(students);
    }

    @Override
    public void deleteStudentById(String id) {
        if (isEmpty(id)) throw new StudentApiException("Students id must not be blank!");

        log.info("StudentService.deleteStudentById = " + id);

        Student studentById = this.getStudentById(id);

        if (nonNull(studentById)) {
            studentRepository.deleteById(id);
        }
    }

    private void validateStudent(Student student) {
        Set<String> nullableFields = Set.of("id", "middleName", "age");
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
                throw new StudentServiceException("Can't get access to student field: " + field.getName());
            }
        }

        if (emptyFields.size() != 0) {
            throw new StudentApiException("Student has no required fields: " + emptyFields);
        }

        validateSemesters(student.getSemesters());
        validateSubjects(student.getSubjects());
    }

    private void validateSemesters(Set<Semester> semesters) {
        if (ObjectUtils.isEmpty(semesters)) {
            throw new StudentApiException("Student has no semesters!");
        }

        boolean isNullRequiredFields = semesters.stream()
                .anyMatch(semester -> isEmpty(semester.getAccepted()) || isEmpty(semester.getCount()));

        if (isNullRequiredFields) {
            throw new StudentApiException("Semester must contains fields: [accepted, count]");
        }
    }

    private void validateSubjects(Set<Subject> subjects) {
        if (ObjectUtils.isEmpty(subjects)) {
            throw new StudentApiException("Student has no subjects!");
        }

        boolean isNullRequiredFields = subjects.stream()
                .anyMatch(subject -> isEmpty(subject.getGrade()) || isEmpty(subject.getName()));

        if (isNullRequiredFields) {
            throw new StudentApiException("Subject must contains fields: [grade, name]");
        }
    }

    private void fillStudentStudentAge(Student student) {
        student.setAge(getAgeFromDate(student.getBirthDate()));
    }

}
