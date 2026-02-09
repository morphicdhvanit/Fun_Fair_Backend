package com.funfair.api.account.role;

public enum RoleType {
	
	ADMIN("admin"),
	SALESPERSON("salesperson"),
	DOORMANAGER("doormanager"),
	ORGANIZER("organizer"),
	CUSTOMER("customer");

	private final String name;

	RoleType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
