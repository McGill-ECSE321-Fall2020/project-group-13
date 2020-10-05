package ca.mcgill.ecse321.projectgroup13.services;

import ca.mcgill.ecse321.projectgroup13.dao.*;

import ca.mcgill.ecse321.projectgroup13.dto.*;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ca.mcgill.ecse321.projectgroup13.model.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Service to handle login and registration of users.
 */
@Service
public class ArtServices {
	public <T> List<T> toList(Iterable<T> iterable) {
        List<T> resultList = new ArrayList<T>();
        for (T t : iterable) {
            resultList.add(t);
        }
        return resultList;
    }

    //IMPORT REPOSITORIES ---> private UserRepository userRepository;
    @Autowired
    private ArtworkRepository artworkRepository;
    // DECLARE 

    //get necessary data from the existing repositories 
    //      // @Transactional
             // public List<User> getAllUsers() {
             //     return toList(userRepository.findAll());
    //      }
    @Transactional
    public List<Artwork> getAllArt() {
        return toList(artworkRepository.findAll());
    } 
    //declare CRUD methods for Art
    //
    




}

