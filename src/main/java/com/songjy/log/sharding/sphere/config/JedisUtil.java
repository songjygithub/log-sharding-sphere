package com.songjy.log.sharding.sphere.config;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author songjy
 * @date 2020-10-16
 */
public class JedisUtil {
    private static final String LOCK_SUCCESS = "OK";

    private JedisPool jedisPool;

    public JedisUtil(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }


    /**
     * 获得队列的长度
     *
     * @param queue 队列
     * @return 队列的长度
     */
    public long llen(String queue) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.llen(queue);
        }
    }

    /**
     * Push(存入 数据到队列中)
     *
     * @param queue 队列
     * @param value 消息
     */
    public void lpush(String queue, Serializable value) {

        if (StringUtils.isBlank(queue) || null == value) {
            return;
        }

        String json = (value.getClass() == String.class) ? value.toString() : JSON.toJSONString(value);
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.lpush(queue, json);
        }
    }

    /**
     * 消息队列：消费
     *
     * @param queue 队列
     * @return 消息
     */
    public List<String> brpop(String queue) {

        if (StringUtils.isBlank(queue)) {
            return Collections.emptyList();
        }

        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.brpop(0, queue);
        }

    }


    /**
     * 向redis中添加Set数据
     *
     * @param key     键
     * @param db      数据库
     * @param expire  超时（秒）
     * @param members 值
     */
    public Long sadd(String key, int db, int expire, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            long r = jedis.sadd(key, members);
            jedis.expire(key, expire);
            return r;
        }
    }

    /**
     * 移除redis之Set中的数据
     *
     * @param key     键
     * @param db      数据库
     * @param members 值
     */
    public Long srem(String key, int db, String... members) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            return jedis.srem(key, members);
        }
    }

    /**
     * 获取redis之Set中的数据
     *
     * @param key 键
     * @param db  数据库
     */
    public Set<String> smembers(String key, int db) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            return jedis.smembers(key);
        }
    }

    /**
     * 查询成员是否在集合中
     *
     * @param key    键
     * @param db     数据库
     * @param member 值
     * @return true|false
     */
    public boolean sismember(String key, int db, String member) {

        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            return jedis.sismember(key, member);
        }
    }

    /**
     * 判定键是否存在
     *
     * @param key 键
     * @param db  数据库
     * @return true|false
     */
    public boolean exists(String key, int db) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            return jedis.exists(key);
        }
    }

    /**
     * 发布消息
     *
     * @param channel 通道
     * @param message 消息
     */
    public void publish(final String channel, final Serializable message) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.publish(channel, String.class == message.getClass() ? message.toString() : JSON.toJSONString(message));
        }
    }

    /**
     * 尝试获取分布式锁
     *
     * @param lockKey     锁
     * @param requestId   请求标识
     * @param millisecond 超期时间(毫秒)
     * @return 是否获取成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int millisecond) {
        try (Jedis jedis = jedisPool.getResource()) {
            //String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, millisecond);
            SetParams px = SetParams.setParams().nx().px(millisecond);
            String result = jedis.set(lockKey, requestId, px);
            return LOCK_SUCCESS.equals(result);
        }
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {
        try (Jedis jedis = jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
            return result.equals(1L);
        }
    }

    /**
     * 自增
     *
     * @param key
     * @param seconds
     * @return 值
     */
    public long incr(String key, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            long incr = jedis.incr(key);
            jedis.expire(key, seconds);
            return incr;
        }
    }

    /**
     * 自增
     *
     * @param key
     * @param v       每次递增多少
     * @param seconds 过期时间（秒）
     * @param db      几号库
     * @return 自增后的值
     */
    public long incrBy(String key, long v, int seconds, int db) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            long incr = jedis.incrBy(key, v);
            jedis.expire(key, seconds);
            return incr;
        }
    }


    /**
     * 递减
     *
     * @param key
     * @param seconds
     * @return 值
     */
    public long decr(String key, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            long incr = jedis.decr(key);
            jedis.expire(key, seconds);
            return incr;
        }
    }

    /**
     * 自增
     *
     * @param key
     * @param seconds
     * @param value
     * @return 值
     */
    public Double incrByFloat(String key, int seconds, double value) {
        try (Jedis jedis = jedisPool.getResource()) {
            Double incr = jedis.incrByFloat(key, value);
            jedis.expire(key, seconds);
            return incr;
        }
    }

    /**
     * 获取redis中set元素个数
     *
     * @param key
     * @param index
     * @return 值
     */
    public Long scard(String key, int index) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(index);
            return jedis.scard(key);
        }
    }

    /**
     * 根据key从指定的数据库中获取数据
     *
     * @param key key
     * @param db  数据库
     * @return value
     */
    public String get(String key, int db) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            return jedis.get(key);
        }
    }

    /**
     * 缓存值
     *
     * @param key           key
     * @param value         value
     * @param db            数据库
     * @param expireSeconds 超时时间（秒）
     * @return OK：成功
     */
    public String set(String key, String value, int db, long expireSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            String result = jedis.set(key, value);
            jedis.expire(key, (int) expireSeconds);
            return result;
        }
    }
}
