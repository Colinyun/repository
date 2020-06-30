package com.colin.core.redis.model;

import java.io.Serializable;
import java.util.HashMap;

public class Responsebody extends HashMap<String, Object> implements Serializable {

	private static final long serialVersionUID = -4205306968098403703L;

	private static final Integer ERROR_CODE = 400500;

	private static final Integer SUCCESS_CODE = 200;

	private static final String MSG = "msg";

	private static Responsebody responsebody = null;

	private Responsebody() {
	}

	public static Responsebody success() {
		if (responsebody == null) {
			responsebody = ResponsebodyHandler.message;
		}
		responsebody.set("code", SUCCESS_CODE);
		return responsebody;
	}

	public static Responsebody success(Object value) {
		if (responsebody == null) {
			responsebody = ResponsebodyHandler.message;
		}
		responsebody.set("code", SUCCESS_CODE);
		responsebody.set(MSG, value);
		return responsebody;
	}

	public static Responsebody fail() {
		if (responsebody == null) {
			responsebody = ResponsebodyHandler.message;
		}
		responsebody.set("code", ERROR_CODE);
		return responsebody;
	}

	public static Responsebody fail(Object value) {
		if (responsebody == null) {
			responsebody = ResponsebodyHandler.message;
		}
		responsebody.set("code", ERROR_CODE);
		responsebody.set(MSG, value);
		return responsebody;
	}

	public Responsebody set(String key, Object message) {
		this.put(key, message);
		return this;
	}

	public <T> T get(String key) {
		return this.get(key);
	}

	private final static class ResponsebodyHandler {
		public static final Responsebody message = new Responsebody();
	}
}
