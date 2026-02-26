package com.funfair.api.ticketavailability;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDetailsDto {
	
	private String eventId;
	private String ticketTypeId;
	private int quantity;
	private double subTotal;
	private double discount;
	private double bookingFee;
	private double totalAmount;
	private double oneticketPrice;
	
	

}
