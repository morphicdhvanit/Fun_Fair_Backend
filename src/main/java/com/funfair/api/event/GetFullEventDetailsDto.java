package com.funfair.api.event;

import java.time.LocalDateTime;
import java.util.List;

import com.funfair.api.account.userrole.AddDoorManagerDto;
import com.funfair.api.account.userrole.AddSalesPersonDetailsDto;
import com.funfair.api.account.userrole.GetDoorManagerDetailsDto;
import com.funfair.api.account.userrole.GetSalsePersonDetailsDto;
import com.funfair.api.tickettypedetails.AddTicketTypeDetailsDto;
import com.funfair.api.tickettypedetails.GetTicketDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetFullEventDetailsDto {
	
	private String organizerId;
	private String eventTitle;
	private String eventCatagory;
	private String eventTags;
	private String eventShortDescription;
	private String entryAllowFor;
	private String ticketNeededFor;
	private LocalDateTime eventStartDateTime;
	private LocalDateTime eventEndDateTime;
	private String eventType;
	private String eventVenueLocaltion;
	private String eventAddressLine1;
	private String eventAddressLine2;
	private String eventCity;
	private String eventState;
	private String eventCountry;
	private String eventZipCode;
	private String googleMapLocationLink;
	private String eventThumbnailImageUrl;
	private String currentStatus;
	private String eventBannerImageUrl;
	private List<String> eventGalleryImageUrl;
	private String ticketType;
	private String eventId;
	private LocalDateTime salseStartDateTime;
	private LocalDateTime salseEndDateTime;
	private String eventTermAndConditions;
	private Boolean isPrivateEvent;
	private String privateEventLink;
	private List<GetDoorManagerDetailsDto> doorManagerDetails;
	private List<GetSalsePersonDetailsDto> salesPersonDetails;
	private List<GetTicketDetailsDto> ticketTypeDetails;
	

	
	
}