package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;



public interface ArtworkRepository extends CrudRepository<Artwork, Long>{

    Set<Artwork> findArtworkByArtist(User artist);
    Set<Artwork> findArtworkByArtist(String username);
    Artwork findArtworkByArtworkID(int artworkID);
    Set<Artwork> findByArtist(User Artist);
    Set<Artwork> getArtworkByisOnPremise(boolean isOnPremise);
    //Set<Artwork> findArtworkByArtistUsername(Set<String> artistsID);
    void deleteArtworkByArtworkID(int artworkID);

}