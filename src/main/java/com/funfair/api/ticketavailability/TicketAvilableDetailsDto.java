package com.funfair.api.ticketavailability;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TicketAvilableDetailsDto {

	
	private String eventname;
	private LocalDateTime eventStartDateTime;
	private LocalDateTime eventEndDateTime;
	private String eventLocation;
	private List<AvilableTicketDetailsDto> availableTicketDetails;
	
}
