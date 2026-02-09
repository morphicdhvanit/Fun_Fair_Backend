package com.funfair.api.event;

public enum EventAgeGroupEnum {
	
	ALL_AGES("all_ages"),
	FIVE_PLUS("five_plus"),
	EIGHTEEN_PLUS("eighteen_plus"),
	SENIOR_CITIZEN("senior_citizen");
	
	private final String name;

	EventAgeGroupEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
