package com.funfair.api.organizer.organizerrequest;

public enum OrganizerRequestStatusEnum {
	
	IN_PROGRESS("in_progress"),
	APPROVED("approved"),
	REJECTED("rejected");

	private final String name;

	OrganizerRequestStatusEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
