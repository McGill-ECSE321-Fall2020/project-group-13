package ca.mcgill.ecse321.projectgroup13.dao;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends CrudRepository<User, String> {

	User findUserByUsernameAndPassword(String username, String password);
    User findUserByUsername(String username);
    User findUserByEmail(String email);
    Boolean deleteUserByUsername(String username);
    //User findUserById(int id);

}