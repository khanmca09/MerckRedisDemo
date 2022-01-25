package com.heroku.devcenter;

import java.net.URI;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class Main {

	public static void main(String[] args) {
		JedisPool pool;
		// establish the connectivity to redis
		try {
			URI redisURI = new URI(System.getenv("REDISTOGO_URL"));
			pool = new JedisPool(new JedisPoolConfig(), redisURI.getHost(), redisURI.getPort(),
					Protocol.DEFAULT_TIMEOUT, redisURI.getUserInfo().split(":", 2)[1]);
		} catch (Exception e) {
			throw new RuntimeException("Redis didn't get configure from REDISTOGO_URL");
		}

		// establist the set/get values from redis instance
		Jedis jedis = pool.getResource();

		try {
			String key = "testkey";
			String value = "testvalue";
			jedis.set(key, value);
			System.out.print("Value set in redis :" + value);
			System.out.print("Value set in redis :" + jedis.get(key));
		} finally {
			pool.returnResource(jedis);
		}
	}

}
