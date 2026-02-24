package com.funfair.api.salespersonticketbookingdetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesPersonSoldTicketTypeDetailsDto {
	
	private String ticketTypeId;
	private String ticketTypeName;
	private int ticketQuantity;
	

}
