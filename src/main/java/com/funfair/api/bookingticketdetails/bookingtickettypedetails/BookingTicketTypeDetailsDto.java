package com.funfair.api.bookingticketdetails.bookingtickettypedetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingTicketTypeDetailsDto {
	
	private String ticketTypeId;
	private int ticketQuantity;
	private double oneTicketPrice;

}
