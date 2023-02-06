package alex.hlo.springboot.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int code;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    private Date timestamp;
    private String message;
    private String path;
    private String contentType;

    public static ErrorMessage initErrorMessage(WebRequest webRequest, HttpStatus httpStatus, String exceptionMessage) {
        return new ErrorMessage(
                httpStatus.value(),
                new Date(),
                exceptionMessage,
                webRequest.getDescription(false),
                webRequest.getHeader("content-type")
        );
    }
}
