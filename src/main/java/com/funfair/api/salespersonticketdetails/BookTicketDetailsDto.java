package com.funfair.api.salespersonticketdetails;

import java.util.List;

import com.funfair.api.salespersonticketdetails.salespersonbookingtickettypedetails.AddSalesPersonBookingTicketTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookTicketDetailsDto {
	
	private String salespersonId;
	private String eventId;
	private String organizerId;
	private String buyerName;
	private String buyernumber;
	private String paymentMethod;
	private double totalPaymentAmount;
	private int Totalquantity;
	private String createdBy;
	private List<AddSalesPersonBookingTicketTypeDto> ticketTypeDetailsList;
}
