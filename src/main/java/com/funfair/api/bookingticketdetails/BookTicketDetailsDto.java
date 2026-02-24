package com.funfair.api.bookingticketdetails;

import java.util.List;

import com.funfair.api.bookingticketdetails.bookingtickettypedetails.BookingTicketTypeDetailsDto;
import com.funfair.api.salespersonticketbookingdetails.PaymnetMethodTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class BookTicketDetailsDto {
	
	private String eventId;
	private String organizerId;
	private String salespersonId;
	private String buyerName;
	private String buyerPhoneNumber;
	private String paymentMethod;
	private int Totalquantity;
	private boolean ticketAssignBySalesPerson;
	private List<BookingTicketTypeDetailsDto> bookTicketDetailsDtos;
	private String createdBy;
}
