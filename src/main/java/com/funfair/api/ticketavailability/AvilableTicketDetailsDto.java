package com.funfair.api.ticketavailability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AvilableTicketDetailsDto {
	
	private String ticketName;
	private double ticketPrice;
	private int quntityAvialable;
	private String ticketTypeId;

}
