package com.funfair.api.event.addeventdetailsdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEventPoliciesRequestDto {

	private String eventId;
	private String TearmsAndConditions;
}
