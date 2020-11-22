package ca.mcgill.ecse321.projectgroup13.services.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RegistrationException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6155380054881909771L;

	public RegistrationException(String msg) {
		super(msg);
	}

}
