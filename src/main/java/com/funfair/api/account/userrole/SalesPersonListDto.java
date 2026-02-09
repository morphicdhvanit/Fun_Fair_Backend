package com.funfair.api.account.userrole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SalesPersonListDto {
	
	private String salesPersonId;
	private String salesPersonName;
	private String salesPersonPhone;
	private String salesPersonEmail;
	private String eventId;
	private String orgId;
}
