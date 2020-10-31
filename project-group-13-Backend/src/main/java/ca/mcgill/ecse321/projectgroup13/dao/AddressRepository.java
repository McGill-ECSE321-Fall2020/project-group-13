package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long>{

	List<Address> findAddressesByUser(User user);
	User findUserByAddressID(Integer id);
    Address findAddressByAddressID(Integer id);
    List<Address> findAddressesByCity(String city);
    Boolean deleteAddressByAddressID(Integer id);
    

}
