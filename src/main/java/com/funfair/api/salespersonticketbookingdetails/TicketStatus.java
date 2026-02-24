package com.funfair.api.salespersonticketbookingdetails;

public enum TicketStatus {
	
	ACTIVE("active"),
	USED("used"),
	CANCELLED("cnacelled");
	
	private final String name;

	TicketStatus(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

}
