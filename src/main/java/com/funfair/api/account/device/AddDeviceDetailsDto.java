package com.funfair.api.account.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddDeviceDetailsDto {
	
	private String deviceId;
	private String deviceToken;
	private String deviceType;
	private String phoneNumber;
	private String roleName;

}
