package com.funfair.api.event;


public enum EventTicketNeededForEnum {

	ALL("all"),
	FIVE_PLUS("5_plus"),
	TEN_PLUS("10_plus"),
	EIGHTEEN_PLUS("18_plus");

	private final String name;

	EventTicketNeededForEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
	

}
