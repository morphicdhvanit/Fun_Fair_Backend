package com.funfair.api.ticketavailability.tickettypeacailability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddTicketTypeAvilabilityDetailsDto {
	
	
	private String ticketName;
	private String ticketTypeId;
	private double ticketPrice;
	private int quntityAvialable;


}
