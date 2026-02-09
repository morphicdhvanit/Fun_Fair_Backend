package com.funfair.api.account.userrole;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddDoorManagerDto {
	
	private String userName;
	private String userphoneNumber;
	private String userEmail;
	private String GateNumber;
	private LocalDateTime gateNumberDate;
	private LocalDateTime roleStartDateTime;
	private LocalDateTime roleEndDateTime;
	private String doorManageGateNumberId;
	private String orgid;
	private String eventId;

}
