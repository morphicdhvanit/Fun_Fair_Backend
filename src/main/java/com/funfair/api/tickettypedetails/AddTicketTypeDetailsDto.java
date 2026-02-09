package com.funfair.api.tickettypedetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddTicketTypeDetailsDto {
	
	private String ticketName;
	private double ticketPrice;
	private int quntityAvialable;
	private String ticketDec;

}
