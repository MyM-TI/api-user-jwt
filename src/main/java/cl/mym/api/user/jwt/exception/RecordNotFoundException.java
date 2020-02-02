package cl.mym.api.user.jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException  extends RuntimeException {
	
	private static final long serialVersionUID = 7722301154866868566L;

	public RecordNotFoundException(String exception) {
        super(exception);
    }

}
