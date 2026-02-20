package com.funfair.api.event;

public enum EventCatagoryEnum {

	MUSIC("Music"),
	THEATER("Theater"),
	COMEDY("Comedy"),
	FAIRS("Fairs"),
	FESTIVALS("Festivals"),
	CULTURAL("Cultural"),
	RELIGIOUS("Religious");

	private final String name;

	EventCatagoryEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
