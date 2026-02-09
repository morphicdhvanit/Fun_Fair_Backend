package com.funfair.api.account.userrole;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSalesPersonDetailsDto {
	
	private String userName;
	private String userphoneNumber;
	private String userEmail;
	private LocalDateTime roleStartDateTime;
	private LocalDateTime roleEndDateTime;
	private String orgid;
	private String eventId;
	

}
