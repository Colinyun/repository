package com.colin.rabbit.mq.enums;

public enum ExchangeType {

	DIRECT("direct"), TOPIC("topic"), HEADERS("headers"), FANOUT("fanout");

	private String type;

	ExchangeType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
