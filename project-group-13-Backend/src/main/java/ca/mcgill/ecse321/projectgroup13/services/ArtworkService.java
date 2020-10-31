package ca.mcgill.ecse321.projectgroup13.services;


import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.dto.ArtworkDto;
import ca.mcgill.ecse321.projectgroup13.dto.UserDto;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.User;
import ca.mcgill.ecse321.projectgroup13.services.exception.illegalArgumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepo;
    @Autowired
    private UserRepository userRepo;


    /**
     * service method to create artwork with a single artist
     * @param artist
     * @return
     */
//    @Transactional
//    public Artwork createArtwork(User artist) {
//        Artwork artwork = new Artwork();
//        artwork.setArtist(new HashSet<User>());
//        artwork.getArtist().add(artist);
//        artworkRepo.save(artwork);
//        return artwork;
//    }
    
    @Transactional
    public Artwork createArtwork(ArtworkDto artworkDto) throws illegalArgumentException {
    	if( artworkDto.getTitle().trim()==null ) throw new illegalArgumentException("no title for artwork");
    	if(artworkDto.getArtist().isEmpty()) throw new illegalArgumentException("no artist for artwork") ;
    	if(artworkDto.getWorth()==0) throw new illegalArgumentException("no worth specified") ;
    	//must convert set of userDTO into set of Users, find them in the database one by one 
    	Set<UserDto> allArtistsDto = artworkDto.getArtist();
    	Iterator<UserDto> artistsDto = allArtistsDto.iterator();
    	
    	Artwork artwork = new Artwork();
    	artwork.setTitle(artworkDto.getTitle());
    	artwork.setDescription(artworkDto.getDescription());
    	artwork.setCreationDate(artworkDto.getCreationDate());
    	artwork.setDimensions(artworkDto.getDimensions());
    	artwork.setIsOnPremise(artworkDto.isIsOnPremise());
    	//TODO might be problematic saving user before saving artwork 
    	//now must add users and maintain referential integrity
    	while (artistsDto.hasNext()) {
    		//for each user
    		User user = userRepo.findUserByUsername(((UserDto)artistsDto.next()).getUsername());
    		if(user==null) throw new illegalArgumentException("artist not found");
    		user.getArtwork().add(artwork);
    		artwork.getArtist().add(user);
    		artworkRepo.save(artwork);
    		userRepo.save(user);
    	}
    	return artwork;
    	
    }
    



    /**
     * service method to delete given artwork from database
     * @param artwork
     */
    @Transactional
    public void deleteArtwork(Artwork artwork){
        artworkRepo.delete(artwork);
    }


    /**
     * service method to delete given artwork from database
     * @param artworkId
     */
    @Transactional
    public void deleteArtworkById(int artworkId){
        artworkRepo.deleteArtworkByArtworkID(artworkId);
    }


    /**
     * service method to return artwork given ID
     * @param artworkID
     * @return
     */
    @Transactional
    public Artwork getArtworkByID(int artworkID) {
        Artwork artwork = artworkRepo.findArtworkByArtworkID(artworkID);
        return artwork;
    }


    /**
     * service method to get all artworks of given user
     * @param username
     * @return
     */
    @Transactional
    public Set<Artwork> getArtworksOfArtist(String username){
        User user = userRepo.findUserByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("Artist does not exist");
        Set<Artwork> artworks = artworkRepo.findArtworkByArtist(username);
        return artworks;
    }



    /**
     * service method to edit artwork description
     * @param artwork
     * @param description
     */
    @Transactional
    public void editArtworkDescription(Artwork artwork, String description){
        artwork.setDescription(description);
    }






}