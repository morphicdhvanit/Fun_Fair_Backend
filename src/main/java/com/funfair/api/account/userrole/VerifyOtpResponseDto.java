package com.funfair.api.account.userrole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpResponseDto {

	private String token;
	private String roleName;
	private String phoneNumber;

	private String userId;

	private String organizerId;

	private String eventId;

}
