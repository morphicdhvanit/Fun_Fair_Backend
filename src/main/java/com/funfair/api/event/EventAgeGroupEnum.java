package com.funfair.api.event;

public enum EventAgeGroupEnum {
	
    ALL_AGES("all_ages", "All Age Groups"),
    FIVE_PLUS("five_plus", "5+ Years"),
    EIGHTEEN_PLUS("eighteen_plus", "18+ Years"),
    SENIOR_CITIZEN("senior_citizen", "Senior Citizen");

    private final String name;
    private final String displayName;

    EventAgeGroupEnum(String name, String displayName) {
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
