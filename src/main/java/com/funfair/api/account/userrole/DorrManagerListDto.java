package com.funfair.api.account.userrole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DorrManagerListDto {
	private String doorManagerId;
	private String doorManagerName;
	private String doorManagerEmail;
	private String doorManagerPhone;
	private String eventName;
	private String eventId;
	private String orgId;
}
