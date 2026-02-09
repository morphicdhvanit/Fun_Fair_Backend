package com.funfair.api.event;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddEventImagesDto {
	
	private String eventId;
	private MultipartFile thumbnailImage;
	private MultipartFile bannerImage;
	private MultipartFile[] galleryImage;

}
