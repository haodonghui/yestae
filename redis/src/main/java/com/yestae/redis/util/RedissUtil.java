package com.yestae.redis.util;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.yestae.tools.PropertiesUtil;

/**
 * <p>
 * rediss通用工具类
 * </p>
 *
 */
public final class RedissUtil {

	private final static String REDIS_CONFIG_PATH = "/properties/redisConfig.properties";
	private static RedissonClient redissonClient=null;

	static {
		
		int redisTimeout = new Integer(PropertiesUtil.getProperty("redis.timeout", REDIS_CONFIG_PATH));
		String redisPassword = PropertiesUtil.getProperty("redis.password",REDIS_CONFIG_PATH);
		int redisDatabase = new Integer(PropertiesUtil.getProperty("redis.database", REDIS_CONFIG_PATH));
		String redisHost = PropertiesUtil.getProperty("redis.host",REDIS_CONFIG_PATH);
		String redisPort = PropertiesUtil.getProperty("redis.port",REDIS_CONFIG_PATH);
		
//		int redisTimeout = 3000;
//		String redisPassword = "m6Kx0s9Vib3r";
//		int redisDatabase = 1;
//		String redisHost = "192.168.100.210";
//		String redisPort = "6379";
		
		StringBuffer redisUrl= new StringBuffer();
		redisUrl.append("http://");
		redisUrl.append(redisHost);
		redisUrl.append(":");
		redisUrl.append(redisPort);
		
		Config config = new Config();
        config.useSingleServer()
        .setAddress(redisUrl.toString())
        .setPassword(redisPassword)
        .setDatabase(redisDatabase)
        .setTimeout(redisTimeout)
        .setConnectionPoolSize(1000);
        ;
        redissonClient = Redisson.create(config);
        
	}

	public static RLock getLock(String name){
		return redissonClient.getLock(name);
	}
	
	public static void unLock(String name){
		redissonClient.getLock(name).unlock();
	}
	
//	public static void main(String[] args) {
//		
//		RLock rlock = RedissUtil.getLock("test");
//		System.out.println(rlock);
//		RedissUtil.unLock("test");
//		System.out.println("ok");
//	}
}
