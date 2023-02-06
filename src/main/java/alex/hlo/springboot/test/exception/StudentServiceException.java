package alex.hlo.springboot.test.exception;

public class StudentServiceException extends StudentApiException {

    public StudentServiceException(String message) {
        super(message);
    }
}
