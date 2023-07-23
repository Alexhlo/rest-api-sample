package alex.hlo.springboot.test.exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }
}
