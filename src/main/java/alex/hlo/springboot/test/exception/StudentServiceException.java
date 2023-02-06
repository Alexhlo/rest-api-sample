package alex.hlo.springboot.test.exception;

public class StudentException extends RuntimeException {

    public StudentException(String message) {
        super(message);
    }
}
