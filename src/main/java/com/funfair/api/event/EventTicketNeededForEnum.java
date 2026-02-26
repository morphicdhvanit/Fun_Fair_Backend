package com.funfair.api.event;

public enum EventTicketNeededForEnum {

	ALL("all", "All Age Groups"), FIVE_PLUS("5_plus", "5+ Years"), TEN_PLUS("10_plus", "10+ Years"),
	EIGHTEEN_PLUS("18_plus", "18+ Years");

	private final String name;
	private String displayName;

	EventTicketNeededForEnum(String name, String displayName) {
		this.name = name;
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}
	
    public String getDisplayName() {
        return displayName;
    }

}
