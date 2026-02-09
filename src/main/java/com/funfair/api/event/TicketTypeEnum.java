package com.funfair.api.event;

public enum TicketTypeEnum {
	
	FREE("free"),
	PAID("paid");

	private final String name;

	TicketTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
