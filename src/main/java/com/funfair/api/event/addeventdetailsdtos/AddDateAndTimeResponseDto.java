package com.funfair.api.event.addeventdetailsdtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddDateAndTimeResponseDto {
	
	private String eventId;
	private LocalDateTime eventStartDateTime;
	private LocalDateTime eventEndDateTime;
	private String organizerId;

}
