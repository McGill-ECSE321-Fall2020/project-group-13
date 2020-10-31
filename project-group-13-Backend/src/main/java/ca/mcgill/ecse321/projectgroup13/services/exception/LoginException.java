package ca.mcgill.ecse321.projectgroup13.services.exception;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class LoginException extends Exception {
	public LoginException(String msg) {
		super(msg);
	}

}
