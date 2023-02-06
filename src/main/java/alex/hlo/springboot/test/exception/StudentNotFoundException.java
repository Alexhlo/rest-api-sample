package alex.hlo.springboot.test.exception;

public class StudentNotFoundException extends StudentApiException {

    public StudentNotFoundException(String message) {
        super(message);
    }
}
