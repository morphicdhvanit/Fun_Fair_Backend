package com.funfair.api.organizer;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateOrganizerDetailsDto {
	
	private String orgName;
	private String orgtEmailId;
	private String orgNumber;
	private MultipartFile userImage;

}
