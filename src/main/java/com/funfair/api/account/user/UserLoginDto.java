package com.funfair.api.account.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserLoginDto {
	
    private String phoneNumber;
    private String otp;

}
