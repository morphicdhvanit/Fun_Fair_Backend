package com.funfair.api.event.eventartistsdetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventArtistsRepository extends JpaRepository<EventArtistsDetails, Integer>{

	List<EventArtistsDetails> findByArtistId(String artistsId);

}
