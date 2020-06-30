package com.colin.core.redis.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.colin.core.redis.properties.RedisProperties;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConditionalOnClass(value = {JedisCluster.class})
public class RedisConfirguration {

	@Autowired
	private RedisProperties redisProoperties;

	@Bean
	public JedisCluster getJedisCluster() {
		String[] nodes = redisProoperties.getNodes().split(",");
		Set<HostAndPort> nodeSet = new HashSet<HostAndPort>();
		for (String node : nodes) {
			nodeSet.add(new HostAndPort(node.split(":")[0], Integer.valueOf(node.split(":")[1])));
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(100);
		config.setMaxIdle(50);
		config.setMinIdle(8);
		config.setMaxWaitMillis(3000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setTestWhileIdle(true);
		return new JedisCluster(nodeSet, redisProoperties.getCommandTimeout(), redisProoperties.getSoTimeout(),
				redisProoperties.getMaxAttempts(), redisProoperties.getPassword(), null, config,
				redisProoperties.isSsl());
	}

}
