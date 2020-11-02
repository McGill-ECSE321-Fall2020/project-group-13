package ca.mcgill.ecse321.projectgroup13.services.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class illegalArgumentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5175098751479258444L;

	public illegalArgumentException(String msg) {
		super(msg);
	}

}
