package ca.mcgill.ecse321.projectgroup13.services.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7111924919493921683L;

	public LoginException(String msg) {
		super(msg);
	}

}
