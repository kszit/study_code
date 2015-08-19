package org.stu_dbredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTools {
	private String redisServers = "172.31.131.205";
	private String redisPort = "6379";
	private static String UserIdKey = "userid";
	
	private JedisPool pool;
	/**
	 * 是否有重复的数据，存在 返回true，
	 *	
	 * @return
	 */
	public boolean repeatId(String id){
		Jedis client = pool.getResource();
		Boolean isContain = client.sismember(UserIdKey, id);
		client.close();
		return isContain;
	}

	
	public void addPationId(String id){
		Jedis client = pool.getResource();
		
		client.sadd(UserIdKey, id);
		
		client.close();
	}
	
	
	
	
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		pool.close();
	}
}
