package alex.hlo.springboot.test.exception;

public class StudentApiException extends RuntimeException {

    public StudentApiException(String message) {
        super(message);
    }
}
