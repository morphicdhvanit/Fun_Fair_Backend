package com.funfair.api.event.addeventdetailsdtos;

import java.time.LocalDateTime;
import java.util.List;

import com.funfair.api.tickettypedetails.AddTicketTypeDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTicketsAndPricingDetailsDto {
	
	private String eventId;
	private String ticketType;
	private LocalDateTime SalesStartDateAndTime;
	private LocalDateTime salesEndDateAndTime;
	private List<AddTicketTypeDetailsDto> ticketTypeDetails;

}
