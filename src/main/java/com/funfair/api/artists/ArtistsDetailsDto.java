package com.funfair.api.artists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistsDetailsDto {
	
	private String artistId;
	private String artistName;
	private String artistImageUrl;
	private String artistBio;

}
