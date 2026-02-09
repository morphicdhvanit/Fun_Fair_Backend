package com.funfair.api.account.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VerifyOtpRequestDto {
	
    private String phoneNumber;
    private String otp;
    private String roleName;

}
