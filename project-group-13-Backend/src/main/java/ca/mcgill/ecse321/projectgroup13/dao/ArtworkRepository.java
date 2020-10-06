package ca.mcgill.ecse321.projectgroup13.dao;

import ca.mcgill.ecse321.projectgroup13.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtworkRepository extends JpaRepository<Artwork, Long>{

    Artwork findArtworkByTitle(String title);
    Artwork findArtworkByArtworkID(String artworkID);

}