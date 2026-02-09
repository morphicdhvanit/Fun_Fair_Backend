package com.funfair.api.event.salespersonticketdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSalesPersonTicketDto {

	private String eventId;
	private String salespersonId;
	private String ticketTypeId;
	private int totalTicketsQuantity;
	private String createdBy;
}
