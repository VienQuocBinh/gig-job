package gigjob.common.exception.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientIdMismatchException extends RuntimeException {
    public ClientIdMismatchException(String message) {
        super(message);
    }
}
