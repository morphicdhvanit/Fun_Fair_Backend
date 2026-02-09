package com.funfair.api.event.addeventdetailsdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrgDetailsResponseDto {
	
	private String eventOrganizerName;
	private String eventId;
	private String orgId;
	private String eventOrganizerEmailId;
	private String eventOrganizerContactNumber; 
	

}
