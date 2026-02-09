package com.funfair.api.account.userrole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendOtpMasterDto {

	private String phoneNumber;
	private String roleName;

	private String deviceId;
	private String deviceToken;
	private String deviceType;
}
