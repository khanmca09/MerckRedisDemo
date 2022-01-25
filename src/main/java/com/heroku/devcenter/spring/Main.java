package com.heroku.devcenter.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Main {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
		JedisPool pool = ctx.getBean(JedisPool.class);
		Jedis jedis = pool.getResource();
		
		try {
			String key = "testkey";
			String value = "testvalueFromSpring";
			jedis.set(key, value);
			System.out.print("Value set in redis spring :" + value);
			System.out.print("Value set in redis spring :" + jedis.get(key));
		} finally {
			pool.returnResource(jedis);
		}
	}

}
