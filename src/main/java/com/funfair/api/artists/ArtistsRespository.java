package com.funfair.api.artists;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistsRespository extends JpaRepository<ArtistsDetails, Integer>{
	
    boolean existsByArtistNameIgnoreCase(String artistName);

	ArtistsDetails findByArtistId(String artistId);

	List<ArtistsDetails> findByArtistNameContainingIgnoreCase(String name);


}
