package ca.mcgill.ecse321.projectgroup13.controller;

import ca.mcgill.ecse321.projectgroup13.dto.*;
import ca.mcgill.ecse321.projectgroup13.services.UserService;
import ca.mcgill.ecse321.projectgroup13.services.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;
	
//Try to route as much as possible through the services
	
	@PostMapping(consumes = "application/json", value = {"/login","/login/"})
	public LoginDto login(@RequestBody LoginDto loginDto) throws LoginException{
			if (loginDto == null) throw new LoginException("empty login");
			userService.login(loginDto);
			return loginDto;
	}
	
	
}
