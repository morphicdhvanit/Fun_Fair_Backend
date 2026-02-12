package com.funfair.api.artists;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArtistsDetailsRequestDto {
	
	private String artistName;
	private String artistBio;
	private MultipartFile artistImage;
	
	

}
