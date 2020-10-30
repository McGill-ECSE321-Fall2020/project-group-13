package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{

	List<Address> findAddressesByUser(User user);
	User findUserByAddressID(int id);
    Address findAddressByAddressID(String tits);

    Address findAddressByAddressID(Integer id);
    User findUserByAddressID(Integer id);
    List<Address> findAddressesByCity(String city);
    Boolean deleteAddressByAddressID(Integer id);
    

    

}
