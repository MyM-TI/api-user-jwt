package cl.mym.api.user.jwt.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import cl.mym.api.user.jwt.model.ResponseMessage;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler 
{
	
	private static final Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);
	
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
    	log.error(ex.getMessage());
    	ResponseMessage error = new ResponseMessage("Server Error");
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
    	log.error(ex.getMessage());
    	ResponseMessage error = new ResponseMessage(ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
    	log.error(ex.getMessage());
    	ResponseMessage error = new ResponseMessage(ex.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
    	log.error(ex.getMessage());
    	ResponseMessage error = new ResponseMessage(ex.getMessage());
        return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	log.error(ex.getMessage());
    	String message = "Not a valid record";
    	for(ObjectError error : ex.getBindingResult().getAllErrors()) {
    		message = error.getDefaultMessage();
    		break;
        }
    	ResponseMessage error = new ResponseMessage(message);
        return new ResponseEntity(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}