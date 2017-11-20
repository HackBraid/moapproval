package com.longfor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.Set;

/**
 * Created by mac on 17/4/25.
 */
@Component
@ConfigurationProperties(prefix = "redis")
public final class RedisUtil {

	private final static Logger logger= LoggerFactory.getLogger(RedisUtil.class);

	//Redis服务器IP
	private  String ADDR;//主 写
	//private static String ADDR = "192.168.33.45";

	//Redis的端口号
	private  int PORT;

	//访问密码
	//private static String AUTH = "lhydsp";
	private  String AUTH;

	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private  int MAXACTIVE;

	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private  int MAXIDLE;

	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private  long MAXWAIT;

	private  int TIMEOUT;

	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private boolean TESTONBORROW;

	public String getADDR() {
		return ADDR;
	}

	public void setADDR(String ADDR) {
		this.ADDR = ADDR;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int PORT) {
		this.PORT = PORT;
	}

	public String getAUTH() {
		return AUTH;
	}

	public void setAUTH(String AUTH) {
		this.AUTH = AUTH;
	}


	public int getTIMEOUT() {
		return TIMEOUT;
	}

	public void setTIMEOUT(int TIMEOUT) {
		this.TIMEOUT = TIMEOUT;
	}

    public int getMAXACTIVE() {
        return MAXACTIVE;
    }

    public void setMAXACTIVE(int MAXACTIVE) {
        this.MAXACTIVE = MAXACTIVE;
    }

    public int getMAXIDLE() {
        return MAXIDLE;
    }

    public void setMAXIDLE(int MAXIDLE) {
        this.MAXIDLE = MAXIDLE;
    }

    public long getMAXWAIT() {
        return MAXWAIT;
    }

    public void setMAXWAIT(long MAXWAIT) {
        this.MAXWAIT = MAXWAIT;
    }

    public boolean isTESTONBORROW() {
        return TESTONBORROW;
    }

    public void setTESTONBORROW(boolean TESTONBORROW) {
        this.TESTONBORROW = TESTONBORROW;
    }

    private  JedisPool pool = null;
    
    
    /**
     * 构建redis连接池
     *
     * @return JedisPool
     */
    public JedisPool getPool() {
        try {
            if (pool== null) {
                JedisPoolConfig config = new JedisPoolConfig();
                //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
                //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
                config.setMaxActive(MAXACTIVE);
                //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
                config.setMaxIdle(MAXIDLE);
                //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
                config.setMaxWait(MAXWAIT);
                //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
                config.setTestOnBorrow(TESTONBORROW);
                pool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            }
        }catch (Exception e){
              throw e;
        }
        return pool;
    }
    
    /**
     * 返还到连接池
     * @param redis
     */
    public void returnResource(Jedis redis) {
        if (redis != null) {
        	 //释放redis对象
        	//RedisUtil.getPool().returnBrokenResource(redis);
        	this.getPool().returnResource(redis);
        }
    }
    
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized Jedis getJedis() {
    	Jedis resource=null;
        try {
            if (this.getPool() != null) {
                resource = this.getPool().getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //释放redis对象
            pool.returnBrokenResource(resource);
            return null;
        }
    }

    /**
     * 通过key获取redis中的数据
     * @param key
     * @return
     */
    public String get(String key)throws Exception{
        String value = null;
        
        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            throw new Exception("通过key获取redis中的数据异常");
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 通过key获取redis中的数据
     * @param key
     * @return
     */
    public void set(String key,int seconds,String value)throws Exception{

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.setex(key,seconds,value);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            throw new Exception("通过key获取redis中的数据异常");
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 推入消息到redis消息通道

     */
    public void publish(String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.publish(channel, message);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 推入消息到redis消息通道
     *
     */
    public void publish(byte[] channel, byte[] message) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.publish(channel, message);
        } finally {
            returnResource(jedis);
        }

    }

    /**
     * 给指定key的队列从左边添加数据
     * @param key
     * @param value
     */
    public void lpush(String key, String value) throws Exception{
		JedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			jedis.lpush(key, value);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
            throw e;
		} finally {
			// 返还到连接池
			returnResource(jedis);
		}
	}
    
    /**
     * 给指定key的队列从右边添加数据
     * @param key
     * @param value
     */
    public void rpush(String key, String value) throws Exception {
		JedisPool pool = null;
		Jedis jedis = null;
        pool = getPool();
        jedis = pool.getResource();
        jedis.lpush(key, value);
        returnResource(jedis);

	}
    
    
    /**
     * 给指定key的队列从左边弹出数据
     * @param key
     */
    public String lpop(String key) throws Exception{
		JedisPool pool = null;
		Jedis jedis = null;
		String value=null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value=jedis.lpop(key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			// 返还到连接池
			returnResource(jedis);
		}
		return value;
	}
    
    /**
     * 给指定key的队列从右边弹出数据
     * @param key
     */
    public String rpop(String key) throws Exception{
		JedisPool pool = null;
		Jedis jedis = null;
		String value=null;
		try {
			pool = getPool();
			jedis = pool.getResource();
			value=jedis.rpop(key);
		} catch (Exception e) {
			// 释放redis对象
			pool.returnBrokenResource(jedis);
			throw e;
		} finally {
			// 返还到连接池
			returnResource(jedis);
		}
		return value;
	}
    
    
    /**
     * @param key 队列的key
     * @param value 要删的数据
     * 删除队列中某条数据
     */
    public boolean deleteOneItemInQueue(String key,String value) throws Exception{
    	Jedis jd = null;
    	try {
    		jd = this.getJedis();
    		this.deleteHandl(jd,key,value);
    		return true;
		} catch (Exception e) {
			// 释放redis对象
			jd.unwatch();
			pool.returnBrokenResource(jd);
			throw e;
		}finally {
			if (jd != null) {
				this.returnResource(jd);
			}
		}
    }
    
    /**
     * 删除队列中的某条数据的处理类
     * @param jd
     * @param key
     * @param value
     */
    private void deleteHandl(Jedis jd,String key,String value)
    {
		jd.watch(key);
    	List<String> list=jd.lrange(key, 0, -1);
		redis.clients.jedis.Transaction jtx = jd.multi();
		jtx.del(key);
		for (String string : list) {
			if(value.equals(string)){
				continue;
			}
			jtx.rpush(key, string);
		}
		if(jtx.exec()==null)
		{
			logger.warn("存在多个客户端操作同一个key的情况，所操作的key:{}",key);
			this.deleteHandl(jd,key,value);
		}
    }

    /**
     * 通过key获取redis中的所有数据
     * @param key
     * @return
     */
    public Set<String> getList(String key)throws Exception{
        Set<String> values = null;

        JedisPool pool = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            values = jedis.keys(key);
        } catch (Exception e) {
            //释放redis对象
            pool.returnBrokenResource(jedis);
            throw new Exception("通过key获取redis中的数据异常");
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
        return values;
    }
}