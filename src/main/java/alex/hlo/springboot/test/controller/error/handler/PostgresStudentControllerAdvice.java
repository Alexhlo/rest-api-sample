package alex.hlo.springboot.test.controller.error.handler;

import alex.hlo.springboot.test.exception.ApiException;
import alex.hlo.springboot.test.exception.NotFoundException;
import alex.hlo.springboot.test.exception.ServiceException;
import alex.hlo.springboot.test.model.ErrorMessage;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class PostgresStudentControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public <T extends RuntimeException> ResponseEntity<ErrorMessage> handleStudentNotFoundException(WebRequest webRequest, T exception) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(initErrorMessage(webRequest, NOT_FOUND, exception.getMessage()));
    }

    @ExceptionHandler({ JsonMappingException.class, ApiException.class, ServiceException.class })
    public <T extends RuntimeException> ResponseEntity<ErrorMessage> handleBadRequestException(WebRequest webRequest, T exception) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(initErrorMessage(webRequest, BAD_REQUEST, exception.getMessage()));
    }

    private ErrorMessage initErrorMessage(WebRequest webRequest, HttpStatus httpStatus, String exceptionMessage) {
        return new ErrorMessage(
                httpStatus.value(),
                LocalDateTime.now(),
                exceptionMessage,
                webRequest.getDescription(false),
                webRequest.getHeader("content-type")
        );
    }
}
