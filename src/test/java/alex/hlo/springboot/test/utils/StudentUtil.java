package alex.hlo.springboot.test.utils;

import alex.hlo.springboot.test.entity.Semester;
import alex.hlo.springboot.test.entity.Student;
import alex.hlo.springboot.test.entity.Subject;
import alex.hlo.springboot.test.model.enums.Gender;
import alex.hlo.springboot.test.model.enums.SubjectGrade;

import java.util.Random;
import java.util.Set;

import static java.util.Objects.requireNonNull;

public final class StudentUtil {

    private StudentUtil() {}

    public static Student generateSimpleStudentModel(long number) {
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

    private static Set<Subject> initSubjects() {
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

    private static SubjectGrade getRandomGrade() {
        SubjectGrade[] grades = SubjectGrade.values();
        int randomIndex = new Random().nextInt(grades.length - 1);
        return grades[randomIndex];
    }
}
