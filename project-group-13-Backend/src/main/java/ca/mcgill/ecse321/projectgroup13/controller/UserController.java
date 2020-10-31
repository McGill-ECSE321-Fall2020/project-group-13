package ca.mcgill.ecse321.projectgroup13.controller;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.dto.*;
import ca.mcgill.ecse321.projectgroup13.model.*;
import ca.mcgill.ecse321.projectgroup13.services.OrderService;
import ca.mcgill.ecse321.projectgroup13.services.ShipmentService;
import ca.mcgill.ecse321.projectgroup13.services.UserService;
import ca.mcgill.ecse321.projectgroup13.services.exception.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
