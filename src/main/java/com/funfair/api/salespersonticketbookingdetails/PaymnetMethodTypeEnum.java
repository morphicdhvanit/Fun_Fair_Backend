package com.funfair.api.salespersonticketbookingdetails;

public enum PaymnetMethodTypeEnum {

	CASH("cash"),
	UPIPAYMNET("upipaymnet"),
	CARD("card");
	
	private final String name;

	PaymnetMethodTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
