package com.funfair.api.event.addeventdetailsdtos;

import com.funfair.api.event.EventTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddLocationDetailsRequestDto {
	
	private String eventId;
	private String eventType;
	private String eventVenueLocaltion;
	private String eventAddressLine1;
	private String eventAddressLine2;
	private String eventCity;
	private String eventState;
	private Double latitude;
	private Double longitude;
	private String eventCountry;
	private String eventZipCode;
	private String googleMapLocationLink;

}
