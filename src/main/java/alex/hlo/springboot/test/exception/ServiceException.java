package alex.hlo.springboot.test.exception;

public class ServiceException extends ApiException {

    public ServiceException(String message) {
        super(message);
    }
}
