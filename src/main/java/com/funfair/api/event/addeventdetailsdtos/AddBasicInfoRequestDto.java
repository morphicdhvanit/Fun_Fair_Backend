package com.funfair.api.event.addeventdetailsdtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddBasicInfoRequestDto {
	
	private String eventId;
	private String eventTitle;
	private String eventCatagory;
	private String eventTags;
	private String eventShortDescription;
	private String entryAllowFor;
	private List<String> ArtistsIds;
	private String ticketNeededFor;
}
