package alex.hlo.springboot.test.service.impl;


import alex.hlo.springboot.test.aspect.Benchmark;
import alex.hlo.springboot.test.aspect.CheckId;
import alex.hlo.springboot.test.entity.Semester;
import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.entity.Subject;
import alex.hlo.springboot.test.exception.ApiException;
import alex.hlo.springboot.test.exception.NotFoundException;
import alex.hlo.springboot.test.exception.ServiceException;
import alex.hlo.springboot.test.repository.StudentRepository;
import alex.hlo.springboot.test.service.student.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
    @CheckId(entityClass = Student.class)
    public Student getStudentById(String id) {
        Student student = studentRepository.findStudentById(id);

        if (isNull(student)) throw new NotFoundException("No student with id: " + id);

        log.info("StudentService.getStudentById = " + student);

        return student;
    }

    @Override
    public List<Student> getStudentsById(List<String> ids) {
        if (isEmpty(ids)) throw new ApiException("Students id list is empty.");

        List<Student> student = studentRepository.findAllById(ids);

        if (isEmpty(student)) throw new NotFoundException("No students with ids: " + ids);

        log.info("StudentService.getStudentById = " + student);

        return student;
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        if (isEmpty(lastName)) throw new ApiException("Students must not be blank!");

        List<Student> students = studentRepository.findAllByLastName(lastName);

        if (isEmpty(students)) throw new NotFoundException("No students with last name: " + lastName);

        log.info("StudentService.getStudentByLastName = " + students);

        return students;
    }

    @Override
    @Benchmark
    @Transactional
    public List<Student> getAllStudents() {
        List<Student> all = studentRepository.findAll();

        if (isEmpty(all)) throw new NotFoundException("Students not found!");

        return all;
    }

    @Override
    public Student saveStudent(Student student) {
        if (isNull(student)) throw new ApiException("Student does not be null!");

        log.info("StudentService.saveStudent = " + student);

        validateStudent(student);
        fillStudentStudentAge(student);

        return studentRepository.save(student);
    }

    @Override
    @Benchmark
    @Transactional
    public List<Student> saveAllStudents(List<Student> students) {
        if (isEmpty(students)) throw new ApiException("Students does not exits!");

        log.info("StudentService.saveAllStudents = " + students);

        students.forEach(student -> {
            validateStudent(student);
            fillStudentStudentAge(student);
        });

        List<Student> savedStudents = studentRepository.saveAll(students);

        if(CollectionUtils.isEmpty(savedStudents)) {
            throw new ServiceException("Students does not saved.");
        }

        return savedStudents;
    }

    @Override
    @CheckId(entityClass = Student.class)
    public void deleteStudentById(String id) {
        log.info("StudentService.deleteStudentById = " + id);

        Student studentById = this.getStudentById(id);

        if (nonNull(studentById)) {
            studentRepository.deleteById(id);
        }
    }

    ///TODO refactor validation

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
                throw new ServiceException("Can't get access to student field: " + field.getName());
            }
        }

        if (emptyFields.size() != 0) {
            throw new ApiException("Student has no required fields: " + emptyFields);
        }

        validateSemesters(student.getSemesters());
        validateSubjects(student.getSubjects());
    }

    private void validateSemesters(Set<Semester> semesters) {
        if (ObjectUtils.isEmpty(semesters)) {
            throw new ApiException("Student has no semesters!");
        }

        boolean isNullRequiredFields = semesters.stream()
                .anyMatch(semester -> isEmpty(semester.getAccepted()) || isEmpty(semester.getCount()));

        if (isNullRequiredFields) {
            throw new ApiException("Semester must contains fields: [accepted, count]");
        }
    }

    private void validateSubjects(Set<Subject> subjects) {
        if (ObjectUtils.isEmpty(subjects)) {
            throw new ApiException("Student has no subjects!");
        }

        boolean isNullRequiredFields = subjects.stream()
                .anyMatch(subject -> isEmpty(subject.getGrade()) || isEmpty(subject.getName()));

        if (isNullRequiredFields) {
            throw new ApiException("Subject must contains fields: [grade, name]");
        }
    }

    private void fillStudentStudentAge(Student student) {
        student.setAge(getAgeFromDate(student.getBirthDate()));
    }

}
