package com.colin.core.redis.fileter;

import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * @auth c-chenyun
 * @date 2020-06-30
 * @describe 基于redsi的布隆拦截器 
 *
 */
public class RedisBloomFileter {

	private int numHashFunctions;
	
	private int bitSize;
	
//	private Funnel
}
