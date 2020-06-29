package com.colin.rabbit.mq.utils;

import java.util.List;
import java.util.Map;

import org.springframework.boot.json.JacksonJsonParser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static ObjectMapper mapper = new ObjectMapper();
	private static JacksonJsonParser jackson = new JacksonJsonParser(mapper);

	public static String toJson(Object data) {
		try {
			return mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObject(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static List<Object> toList(String json) {
		return jackson.parseList(json);
	}

	public static Map<String, Object> toMap(String json) {
		return jackson.parseMap(json);
	}

	public static byte[] toBytes(Object object) {
		try {
			return mapper.writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
