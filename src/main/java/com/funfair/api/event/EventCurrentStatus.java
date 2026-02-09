package com.funfair.api.event;

public enum EventCurrentStatus {
	DRAFT("draft"),
	UPCOMING("upcoming"),
	LIVE("live"),
	COMPLETED("completed");
	
	private final String name;

	EventCurrentStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
