package com.funfair.api.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerHomeEventDetailsDto {
	
	
	private String eventId;
	private String eventName;
	private String thumbnailImageUrl;
	private String eventStartDateTime;
	private String eventAddressLine1;
	private String eventAddressLine2;
	private Double eventprice;
	private String eventEndDateTime;

}
