package com.funfair.api.event.addeventdetailsdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLocaltionDetailsResponseDto {
	
	private String eventId;
	private String eventType;
	private String eventVenueLocaltion;
	private String eventAddressLine1;
	private String eventAddressLine2;
	private String eventCity;
	private String eventState;
	private String eventCountry;
	private String eventZipCode;
	private String googleMapLocationLink;


}
