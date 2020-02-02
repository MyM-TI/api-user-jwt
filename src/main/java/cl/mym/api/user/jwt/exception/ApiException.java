package cl.mym.api.user.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 3895888052665040707L;

	public ApiException(String exception) {
        super(exception);
    }
}

