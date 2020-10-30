package ca.mcgill.ecse321.projectgroup13;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories
public class ProjectGroup13Application {

	public static void main(String[] args) {
		SpringApplication.run(ProjectGroup13Application.class, args);
	}

	@RequestMapping("/")
	public String greeting(){
		return "Hello world!";
	}

}