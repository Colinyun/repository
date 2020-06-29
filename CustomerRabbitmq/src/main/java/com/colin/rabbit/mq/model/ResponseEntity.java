package com.colin.rabbit.mq.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = -6510662625994252266L;

	private List<T> _dataList;

	private Map<String, T> _dataMap;

	private int code = 200;

	public ResponseEntity() {
		_dataList = new ArrayList<T>();
		_dataMap = new HashMap<String, T>();
	}

	public static <T> ResponseEntity<T> build() {
		return new ResponseEntity<T>();
	}

	public List<T> getList() {
		return _dataList;
	}

	public Map<String, T> getMap() {
		return _dataMap;
	}

	public int getCode() {
		return code;
	}

	public ResponseEntity<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public ResponseEntity<T> addAll(List<T> _dataList) {
		this._dataList.addAll(_dataList);
		return this;
	}

	public ResponseEntity<T> add(T value) {
		this._dataList.add(value);
		return this;
	}

	public ResponseEntity<T> putAll(Map<String, T> _dataMap) {
		this._dataMap.putAll(_dataMap);
		return this;
	}

	public ResponseEntity<T> put(String key, T value) {
		this._dataMap.put(key, value);
		return this;
	}

	public T get(int index) {
		return this._dataList.size() > index ? null : this._dataList.get(index);
	}

	public T get(String key) {
		return this._dataMap.containsKey(key) ? this._dataMap.get(key) : null;
	}
}
