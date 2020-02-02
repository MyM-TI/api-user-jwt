package cl.mym.api.user.jwt.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends RuntimeException {

	private static final long serialVersionUID = 2244638893306214968L;
	
	private final String message;
	  private final HttpStatus httpStatus;

	  public TokenException(String message, HttpStatus httpStatus) {
	    this.message = message;
	    this.httpStatus = httpStatus;
	  }

	  @Override
	  public String getMessage() {
	    return message;
	  }

	  public HttpStatus getHttpStatus() {
	    return httpStatus;
	  }

	}

