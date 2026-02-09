package com.funfair.api.account.userrole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SendOtpDetailsDto {
	
	
	private String deviceId;
	private String roleName;
	private String deviceToken;
	private String deviceType;
	private String phoneNumber;


}
