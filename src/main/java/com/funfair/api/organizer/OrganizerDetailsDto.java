package com.funfair.api.organizer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerDetailsDto {
	
	private String orgId;
	private String orgName;
	private String orgtEmailId;
	private String orgNumber;
	private String orgUserId;

}
