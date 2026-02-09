package com.funfair.api.account.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
			
public class UserDetailsDto {
	
	private String userId;
	private String userName;
	private String emailId;
	private String phoneNo;
	private String userImage;
	public String generatedToken;
	private String createdBy;
	private String createdOn;
}
