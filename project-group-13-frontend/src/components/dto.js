function ArtworkDto(
  artworkID,
  title,
  isOnPremise,
  worth,
  artworkSold,
  description,
  creationDate,
  dimensions,
  medium,
  collection,
  imageUrl,
  artists
) {
    this.isOnPremise = isOnPremise;
        this.artists = artists;
        this.artworkID = artworkID;
        this.worth = worth;
        this.artworkSold = artworkSold;
        this.description = description;
        this.title = title;
        this.creationDate = creationDate;
        this.dimensions = dimensions;
        this.medium = medium;
        this.collection = collection;
        this.imageUrl = imageUrl;
}
