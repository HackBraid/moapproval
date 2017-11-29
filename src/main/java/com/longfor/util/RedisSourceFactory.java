package com.longfor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * redis工厂对象
 *
 */
public class RedisSourceFactory {
	//数据源路由
	private static Map<String, JedisPool> map = null;
	//日志
	private static Logger logger = LoggerFactory.getLogger(RedisSourceFactory.class);
	
//	private static String redisUrl="192.168.11.141";
	
//	static {
//		try {
//			map=new HashMap<String, JedisPool>();
//			JedisPoolConfig config = new JedisPoolConfig();
//			config.setMaxActive(-1);
//			config.setMaxIdle(200);
//			config.setMaxWait(10000);
//			config.setTestOnBorrow(true);
//			JedisPool pool = new JedisPool(config,redisUrl,6379,10000,"worko");
//			map.put("common", pool);
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			throw new RuntimeException(e.getMessage(), e);
//		}
//	}

	public RedisSourceFactory(String redisIp, int port, String password){
		map=new HashMap<String, JedisPool>();
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(-1);
		config.setMaxIdle(200);
		config.setMaxWait(10000);
		config.setTestOnBorrow(true);
		JedisPool pool = new JedisPool(config,redisIp,port,10000,password);
		map.put(redisIp, pool);
	}

	/**
	 * 获取连接
	 * 
	 * @version 	2014-6-22
	 * @param id	数据库标识
	 */
	public Jedis getRedis(String id) {
		Jedis redis = null;
		try {
			if (map != null) {
				JedisPool pool = map.get(id);
				if (pool != null) {
					redis=pool.getResource();
				}
			}
			if(redis==null){
				throw new RuntimeException("Get redis error");
			}
			return redis;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 放回连接
	 * 
	 * @version 	2014-6-22
	 * @param id	数据库标识
	 * @param redis	连接对象
	 */
	public static void closeRedis(String id, Jedis redis) {
		try {
			if (map != null && redis != null) {
				JedisPool pool = map.get(id);
				if (pool != null) {
					pool.returnResource(redis);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
