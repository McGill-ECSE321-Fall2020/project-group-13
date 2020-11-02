package ca.mcgill.ecse321.projectgroup13.services;


import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.CartRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.dto.ArtworkDto;
import ca.mcgill.ecse321.projectgroup13.dto.UserDto;
import ca.mcgill.ecse321.projectgroup13.model.Address;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CartService cartService;


//    /**
//     * service method to create artwork with a single artist
//     * @param artist
//     * @return
//     */
//    @Transactional
//    public Artwork createArtwork(String artistUsername, String title, Double worth ) {
//        User user = userRepo.findUserByUsername(artistUsername);
//        if(artistUsername==null) throw new IllegalArgumentException("invalid user");
//        artwork.setArtist(newartists);
//        Set<Artwork> works= user.getArtwork();
//        works.add(artwork);
//        user.setArtwork(works);
//        artworkRepo.save(artwork);
//        userRepo.save(user);
//        return artwork;
//    }
    
//    @Transactional
//    public Artwork createArtwork(ArtworkDto artworkDto) throws IllegalArgumentException {
//    	if(artworkDto.getTitle().trim()==null ) throw new IllegalArgumentException("no title for artwork");
//    	if(artworkDto.getArtist().isEmpty()) throw new IllegalArgumentException("no artist for artwork") ;
//    	if(artworkDto.getWorth()==0) throw new IllegalArgumentException("no worth specified") ;
//    	
//    	//must convert set of userDTO into set of Users, find them in the database one by one 
//    	Set<UserDto> allArtistsDto = artworkDto.getArtist();
//    	Iterator<UserDto> artistsDto = allArtistsDto.iterator();
//    	
//    	Artwork artwork = new Artwork();
//    	artwork.setTitle(artworkDto.getTitle());
//    	artwork.setDescription(artworkDto.getDescription());
//    	artwork.setCreationDate(artworkDto.getCreationDate());
//    	artwork.setDimensions(artworkDto.getDimensions());
//    	artwork.setIsOnPremise(artworkDto.isIsOnPremise());
//    	//TODO might be problematic saving user before saving artwork 
//    	//now must add users and maintain referential integrity
//    	while (artistsDto.hasNext()) {
//    		//for each user
//    		User user = userRepo.findUserByUsername(((UserDto)artistsDto.next()).getUsername());
//    		if(user==null) throw new IllegalArgumentException("artist not found");
//    		user.getArtwork().add(artwork);
//    		artwork.getArtist().add(user);
//    		artworkRepo.save(artwork);
//    		userRepo.save(user);
//    	}
//    	return artwork;
//    	
//    }

    /**
     * service method to create artwork with many artists
     * @param Title
     * @param usernames
     * @param worth
     * @return
     * @throws IllegalArgumentException
     */
    public Artwork createArtwork(String Title, String[] usernames, Double worth) throws IllegalArgumentException {
    	if( Title==null || Title.trim()=="" ) throw new IllegalArgumentException("invalid title");
    	if(usernames == null || usernames.length==0) throw new IllegalArgumentException("invalid user") ;
    	if(worth==null||worth==0) throw new IllegalArgumentException("invalid worth") ;

    	Artwork artwork = new Artwork();
    	for(int i = 0; i < usernames.length; i++){
    		String name = usernames[i];
    		User user = userRepo.findUserByUsername(name);
    		if(user==null) throw new IllegalArgumentException("invalid user");
    		artwork.setArtist(new HashSet<>());
    		//Set<User> artists= artwork.getArtist();
            artwork.getArtist().add(user);
    		//artists.add(user);
    		//artwork.setArtist(artists);
    		artwork.setTitle(Title);
    		artwork.setWorth(worth);
    		Set<Artwork> works= user.getArtwork();
    		works.add(artwork);
    		//user.setArtwork(works);
    		artworkRepo.save(artwork);
    		//userRepo.save(user);
    	}
    	return artwork;
    }



    /**
     * service method to delete given artwork from database
     * also removes it from cart to which it belongs
     * @param artwork
     */
    @Transactional
    public Boolean deleteArtwork(Artwork artwork){
        Set<User> artists = artwork.getArtist();
        for(User artist : artists){
            Set<Artwork> artworks = artist.getArtwork();
            artworks.remove(artwork);
            artist.setArtwork(artworks);
        }
        Set<Cart> carts = cartRepo.findCartsByArtwork(artwork);
        for(Cart c : carts){
            cartService.removeFromCart(c, artwork);
        }
        artworkRepo.delete(artwork);
        return true; 
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
        Set<Artwork> artworks = artworkRepo.findArtworkByArtist(user);
        return artworks;
    }



    /**
     * service method to edit artwork description
     * @param artwork
     * @param description
     */
    @Transactional
    public void editArtwork_description(Artwork artwork, String description){
        artwork.setDescription(description);
        artwork = artworkRepo.save(artwork);
    }

    /**
     * edits title
     * @param artwork
     * @param title
     */
    @Transactional
    public void editArtwork_title(Artwork artwork, String title){
        artwork.setTitle(title);
        artwork = artworkRepo.save(artwork);
    }
    /**
     * Edits creation date. 
     * @param artwork
     * @param date
     */
    @Transactional
    public void editArtwork_creationDate(Artwork artwork, String date){
        artwork.setCreationDate(date);
        artwork = artworkRepo.save(artwork);
    }
    /**
     * edits dimention
     * @param artwork
     * @param dimensions
     */
    @Transactional
    public void editArtwork_dimensions(Artwork artwork, String dimensions){
        artwork.setDimensions(dimensions);
        artwork = artworkRepo.save(artwork);
    }
    /**
     * edits medium of selected artwork
     * @param artwork
     * @param medium
     */
    @Transactional
    public void editArtwork_medium(Artwork artwork, String medium){
        artwork.setMedium(medium);
        artwork = artworkRepo.save(artwork);
    }
    /**
     * edits the collection field. Whose artwork is it?
     * @param artwork
     * @param collection
     */
    @Transactional
    public void editArtwork_collection(Artwork artwork, String collection){
        artwork.setCollection(collection);
        artwork = artworkRepo.save(artwork);
    }
    @Transactional
    public void editArtwork_imageURL(Artwork artwork, String image){
        artwork.setImageUrl(image);
        artwork = artworkRepo.save(artwork);
    }
    @Transactional
    public void setArtwork_artworkSold(Artwork artwork){
        artwork.setArtworkSold(true);
        artwork = artworkRepo.save(artwork);
    }
    
    @Transactional
    public void editArtwork_isOnPremise(Artwork artwork, boolean onPremise){
        artwork.setIsOnPremise(onPremise);
        artwork = artworkRepo.save(artwork);
    }
    @Transactional
    public void editArtwork_worth(Artwork artwork, double worth){
        artwork.setWorth(worth);
        artwork = artworkRepo.save(artwork);
    }

}