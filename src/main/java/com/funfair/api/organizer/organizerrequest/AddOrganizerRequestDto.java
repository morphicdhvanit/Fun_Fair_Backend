package com.funfair.api.organizer.organizerrequest;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrganizerRequestDto {

	private String organizerName;
	private String organizerEmailId;	
	private String organizernumber;
	private String organizerEventCategory;
	private String organizerEventTitle;
	private String organizerCity;
	private String organizerShortDec;
	
	private MultipartFile userImage;
}
