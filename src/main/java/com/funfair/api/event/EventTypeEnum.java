package com.funfair.api.event;

public enum EventTypeEnum {
	
	ON_LOCATION("on_location"),
	PRIVATE("private");
	
	private final String name;

	EventTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	

}
