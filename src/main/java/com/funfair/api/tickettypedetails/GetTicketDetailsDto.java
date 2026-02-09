package com.funfair.api.tickettypedetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetTicketDetailsDto {
	
	
	private String ticketName;
	private String eventId;
	private String 	TicketTypeId;
	private double ticketPrice;
	private int quntityAvialable;
	private String orgId;
	private String ticketDec;

}
