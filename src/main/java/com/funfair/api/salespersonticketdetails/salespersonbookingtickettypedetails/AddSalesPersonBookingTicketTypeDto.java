package com.funfair.api.salespersonticketdetails.salespersonbookingtickettypedetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSalesPersonBookingTicketTypeDto {
	
	private String ticketTypeId;
	private int ticketQuantity;
	private double oneTicketPrice;

}
