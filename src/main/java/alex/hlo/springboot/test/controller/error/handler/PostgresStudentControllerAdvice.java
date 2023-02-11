package alex.hlo.springboot.test.controller.error.handler;

import alex.hlo.springboot.test.exception.StudentApiException;
import alex.hlo.springboot.test.exception.StudentNotFoundException;
import alex.hlo.springboot.test.exception.StudentServiceException;
import alex.hlo.springboot.test.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class PostgresStudentControllerAdvice {

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleStudentNotFoundException(WebRequest webRequest, StudentNotFoundException exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(initErrorMessage(webRequest, NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler({ StudentApiException.class, StudentServiceException.class })
    public ResponseEntity<ErrorMessage> handleExceptionException(WebRequest webRequest, StudentApiException exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(initErrorMessage(webRequest, BAD_REQUEST, exception.getMessage()));
    }

    private ErrorMessage initErrorMessage(WebRequest webRequest, HttpStatus httpStatus, String exceptionMessage) {
        return new ErrorMessage(
                httpStatus.value(),
                new Date(),
                exceptionMessage,
                webRequest.getDescription(false),
                webRequest.getHeader("content-type")
        );
    }
}
