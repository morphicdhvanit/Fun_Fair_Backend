package com.funfair.api.ticketavailability;

import java.util.List;

import com.funfair.api.ticketavailability.tickettypeacailability.AddTicketTypeAvilabilityDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTicketAvilabilityDetailDto {
	
	
	private String eventId;
	private String orgId;
	private int bookingPrice;
	private int totalTickets;
	private String createdBy;
	private List<AddTicketTypeAvilabilityDetailsDto> ticketTypeAvilabilityDetails;
	

}
