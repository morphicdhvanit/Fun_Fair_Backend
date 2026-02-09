package com.funfair.api.organizer.organizerrequest;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class AllOrganizerRequestDto {
	
	private String organizerRequestId;
	private String organizerName;
	private String organizernumber;
	private String organizerEmailId;
	private String organizerImagePath;
	private String organizerEventCategory;
	private String organizerEventTitle;
	private String organizerCity;
	private String organizerShortDec;
	private String organizerRequestStatus;
	private String createdBy;
	private LocalDateTime createdOn;
}
