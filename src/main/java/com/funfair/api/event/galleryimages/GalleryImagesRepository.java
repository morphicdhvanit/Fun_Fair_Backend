package com.funfair.api.event.galleryimages;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryImagesRepository extends JpaRepository<GalleryImagesDetails, Integer>{

	List<GalleryImagesDetails> findByEventId(String eventId);

}
