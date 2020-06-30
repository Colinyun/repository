package com.colin.core.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("spring.redis.cluster")
public class RedisProperties {

	private String nodes;

	private int expireSeconds;

	private int commandTimeout;

	private int soTimeout;

	private int maxAttempts;

	private String user;

	private String password;

	private boolean isSsl;

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public int getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public int getCommandTimeout() {
		return commandTimeout;
	}

	public void setCommandTimeout(int commandTimeout) {
		this.commandTimeout = commandTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getMaxAttempts() {
		return maxAttempts;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isSsl() {
		return isSsl;
	}

	public void setSsl(boolean isSsl) {
		this.isSsl = isSsl;
	}

	@Override
	public String toString() {
		return "RedisProperties [nodes=" + nodes + ", expireSeconds=" + expireSeconds + ", commandTimeout="
				+ commandTimeout + ", soTimeout=" + soTimeout + ", maxAttempts=" + maxAttempts + ", user=" + user
				+ ", password=" + password + ", isSsl=" + isSsl + "]";
	}

}
