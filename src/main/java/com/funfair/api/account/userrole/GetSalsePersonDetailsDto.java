package com.funfair.api.account.userrole;

import java.time.LocalDateTime;
import java.util.List;

import com.funfair.api.event.salespersonticketdetails.AddSalesPersonTicketDto;
import com.funfair.api.event.salespersonticketdetails.SalesPersonTicketDetailsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetSalsePersonDetailsDto {
	
	private String userName;
	private String userphoneNumber;
	private String userEmail;
	private LocalDateTime roleStartDateTime;
	private LocalDateTime roleEndDateTime;
	private String orgid;
	private String eventId;
	private String userImage;
	private String userId;
	
	private List<SalesPersonTicketDetailsDto> salesPersonTicketDetails;

}
