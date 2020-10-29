package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import ca.mcgill.ecse321.projectgroup13.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;



public interface ArtworkRepository extends CrudRepository<Artwork, Long>{

    Artwork findArtworkByTitle(String title);
    Artwork findArtworkByArtist(User artist);
    Artwork findArtworkByArtworkID(String artworkID);
    List<Artwork> findByArtist(User Artist);
    //Set<Artwork> findArtworkByArtistUsername(Set<String> artistsID);

}