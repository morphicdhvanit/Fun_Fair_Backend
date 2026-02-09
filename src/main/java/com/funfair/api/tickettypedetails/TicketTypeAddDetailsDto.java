package com.funfair.api.tickettypedetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeAddDetailsDto {
	
	private String ticketName;
	private int quntityAvialable;
	private String ticketDec;
	private String tickettypeId;
	private String orgId;
	private String eventId;
}
