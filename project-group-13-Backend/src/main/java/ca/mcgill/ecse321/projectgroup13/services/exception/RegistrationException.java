package ca.mcgill.ecse321.projectgroup13.services.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RegistrationException extends Exception {
	public RegistrationException(String msg) {
		super(msg);
	}

}
