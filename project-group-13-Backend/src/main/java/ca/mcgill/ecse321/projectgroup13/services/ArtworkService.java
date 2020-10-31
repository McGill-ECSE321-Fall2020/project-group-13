package ca.mcgill.ecse321.projectgroup13.services;


import ca.mcgill.ecse321.projectgroup13.dao.ArtworkRepository;
import ca.mcgill.ecse321.projectgroup13.dao.UserRepository;
import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
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
    @Transactional
    public Artwork createArtwork(User artist) {
        Artwork artwork = new Artwork();
        artwork.setArtist(new HashSet<User>());
        artwork.getArtist().add(artist);
        artworkRepo.save(artwork);
        return artwork;
    }


    /**
     * service method to create artwork (specifying all artists)
     * @param artists
     * @return
     */
    @Transactional
    public Artwork createArtwork(Set<User> artists) {
        Artwork artwork = new Artwork();
        artwork.setArtist(artists);
        artworkRepo.save(artwork);
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