package com.colin.rabbit.mq.model;

import java.io.Serializable;
import java.util.HashMap;

public class ResultMessage extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = 1902702966759369160L;

	private static final Integer ERROR_CODE = 400500;

	private static final Integer SUCCESS_CODE = 200;

	private static ResultMessage message = null;

	private ResultMessage() {
	}

	public static ResultMessage success() {
		if (message == null) {
			message = ResultMessageHandler.message;
		}
		message.set("code", SUCCESS_CODE);
		return message;
	}

	public static ResultMessage fail() {
		if (message == null) {
			message = ResultMessageHandler.message;
		}
		message.set("code", ERROR_CODE);
		return message;
	}

	public ResultMessage setMsg(Object message) {
		this.put("msg", message);
		return this;
	}

	public ResultMessage set(String key, Object message) {
		this.put(key, message);
		return this;
	}

	public <T> T get(String key) {
		return this.get(key);
	}

	private final static class ResultMessageHandler {
		public static final ResultMessage message = new ResultMessage();
	}
}
