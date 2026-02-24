package com.funfair.api.salespersonticketbookingdetails;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesPersonSoldTicketDetailsDto {
	
	private String eventId;
	private String eventName;
	private String buyerName;
	private String buyerPhoneNumber;
	private LocalDateTime ticketpurchaseDateTime;
	private List<SalesPersonSoldTicketTypeDetailsDto> salesPersonSoldTicketTypeDetailsDtos;
	

}
