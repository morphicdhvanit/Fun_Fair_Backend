package com.funfair.api.artists;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistsRespository extends JpaRepository<ArtistsDetails, Integer>{
	
    boolean existsByArtistNameIgnoreCase(String artistName);

	ArtistsDetails findByArtistId(String artistId);


}
