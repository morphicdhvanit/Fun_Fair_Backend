package com.funfair.api.event.gatenodetails;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddGateNoDto {
	
	
	private String gateNo;
	private LocalDateTime gateNumberDate;

}
