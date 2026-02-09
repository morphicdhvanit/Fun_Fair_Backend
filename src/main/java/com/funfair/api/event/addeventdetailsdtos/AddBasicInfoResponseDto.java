package com.funfair.api.event.addeventdetailsdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBasicInfoResponseDto {
	
	private String eventId;
	private String eventTitle;
	private String eventCatagory;
	private String eventTags;
	private String eventShortDescription;
	private String entryAllowFor;
	private String ticketNeededFor;
}