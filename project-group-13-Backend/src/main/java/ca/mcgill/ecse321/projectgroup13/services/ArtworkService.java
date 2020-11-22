package ca.mcgill.ecse321.projectgroup13.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ca.mcgill.ecse321.projectgroup13.dao.OrderRepository;
import ca.mcgill.ecse321.projectgroup13.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.CartRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.Cart;
import ca.mcgill.ecse321.projectgroup13.model.User;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private CartService cartService;



    /**
     * service method to create artwork with many artists
     * @param Title
     * @param usernames
     * @param worth
     * @param url	Base64 encoding of URL
     * @return
     * @throws IllegalArgumentException
     */
    public Artwork createArtwork(String Title, String[] usernames, Double worth, String url) throws IllegalArgumentException {
    	if( Title==null || Title.trim().contentEquals("") ) throw new IllegalArgumentException("invalid title");
    	if(usernames == null || usernames.length==0) throw new IllegalArgumentException("invalid user") ;
    	if(worth==null||worth==0) throw new IllegalArgumentException("invalid worth") ;

    	Artwork artwork = new Artwork();
		artwork.setTitle(Title);
		artwork.setWorth(worth);
		artwork.setImageUrl(url);    
		artwork.setArtist(new HashSet<>());
        Set<User> artists = artwork.getArtist();
    	for(String username : usernames){
    		User user = userRepo.findUserByUsername(username);
    		if(user==null) throw new IllegalArgumentException("invalid user");
            artists.add(user);
            System.out.println(artists);
            System.out.println("works "+artwork.getArtist());
    		Set<Artwork> ArtistArtworks = user.getArtwork();
    		ArtistArtworks.add(artwork);
    		user.setArtwork(ArtistArtworks);
    	}
    	artwork.setArtist(artists);
    	artworkRepo.save(artwork);
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
     * service method to return all artworks
     * @param artworkID
     * @return
     */
    @Transactional
    public Iterable<Artwork> getAllArtworks() {
        return  artworkRepo.findAll();
    }


    /**
     * service method to get all artworks of given artist
     * @param username
     * @return
     */
    @Transactional
    public Set<Artwork> getArtworksOfArtist(String username){
        User user = userRepo.findUserByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("Artist does not exist");
        Set<Artwork> artworks = artworkRepo.getArtworkByArtist(user);
        return artworks;
    }

    /**
     * service method to get all artworks of an order
     * @param orderId
     * @return
     */
    @Transactional
    public Set<Artwork> getArtworksOfOrder(int orderId){
        Order order = orderRepo.findOrderByOrderID(orderId);
        if (order == null)
            throw new IllegalArgumentException("Order does not exist");
        Set<Artwork> artworks = artworkRepo.findArtworkByOrder(order);
        return artworks;
    }
    
    /**
     * service method to get all artworks of a given category
     * @param category
     * @return
     */
    @Transactional
    public Set<Artwork> getArtworkByCategory(String category){
        Set<Artwork> artworks = artworkRepo.getArtworkByMedium(category);
        return artworks;
    }

         /**
     * service method to get all artworks with a given title -- it is not case sensitive
     * @param title
     * @return
     */
    @Transactional
    public Set<Artwork> getArtworksSearchTitle( String title) {
        Set<Artwork> artworks = artworkRepo.findAll();
        Set<Artwork> newSet = new HashSet<Artwork>();
        artworks.stream().filter(x -> x.getTitle().toLowerCase().contains(title.toLowerCase())).forEach(newSet::add);
        return newSet;
    }

    /**
     * service method to get all artworks that are on the gallery premises
     * @param isOnPremise
     * @return
     */
    @Transactional
    public Set<Artwork> getArtworksOnPremise(boolean isOnPremise){
        Set<Artwork> artworks = artworkRepo.getArtworkByisOnPremise(isOnPremise);
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

    /**
     * change artwork image displays
     * @param artwork
     * @param image
     */
    @Transactional
    public void editArtwork_imageURL(Artwork artwork, String image){
        artwork.setImageUrl(image);
        artwork = artworkRepo.save(artwork);
    }

    /**
     * change status of artwork sold/not sold
     * @param artwork
     */
    @Transactional
    public void setArtwork_artworkSold(Artwork artwork){
        artwork.setArtworkSold(true);
        artwork = artworkRepo.save(artwork);
    }

    /**
     * change isOnPremise attribute
     * @param artwork
     * @param onPremise
     */
    @Transactional
    public void editArtwork_isOnPremise(Artwork artwork, boolean onPremise){
        artwork.setIsOnPremise(onPremise);
        artwork = artworkRepo.save(artwork);
    }

    /**
     * change artwork price
     * @param artwork
     * @param worth
     */
    @Transactional
    public void editArtwork_worth(Artwork artwork, double worth){
        artwork.setWorth(worth);
        artwork = artworkRepo.save(artwork);
    }

}