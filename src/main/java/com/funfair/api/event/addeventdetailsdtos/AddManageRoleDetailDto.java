package com.funfair.api.event.addeventdetailsdtos;

import java.time.LocalDateTime;
import java.util.List;

import com.funfair.api.event.gatenodetails.AddGateNoDto;
import com.funfair.api.event.salespersonticketdetails.AddSalesPersonTicketDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddManageRoleDetailDto {
	
	private String userName;
	private String userphoneNumber;
	private String userEmail;
	private LocalDateTime roleStartDateTime;
	private LocalDateTime roleEndDateTime;
	private String role;
	private String orgid;
	private String eventId;
	private List<AddSalesPersonTicketDto> addSalesPersonTicketDtos;

//	door Manger details
	private List<AddGateNoDto> gateNoDetails;
}
