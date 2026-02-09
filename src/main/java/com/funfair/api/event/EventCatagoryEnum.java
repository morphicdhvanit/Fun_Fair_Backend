package com.funfair.api.event;

public enum EventCatagoryEnum {

	MUSIC("music"),
	THEATER("theater"),
	COMEDY("comedy"),
	FAIRS("fairs"),
	FESTIVALS("festivals"),
	CULTURAL("cultural"),
	RELIGIOUS("religious");

	private final String name;

	EventCatagoryEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
