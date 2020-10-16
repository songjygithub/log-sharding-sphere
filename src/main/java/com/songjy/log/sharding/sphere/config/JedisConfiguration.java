package com.songjy.log.sharding.sphere.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author songjy
 */
@Configuration
public class JedisConfiguration {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public JedisUtil jedisUtil() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(200);
        config.setMinIdle(5);
        config.setMaxWaitMillis(100);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        config.setTestWhileIdle(true);

        JedisPool jedisPool;

        if (StringUtils.isBlank(password)) {
            jedisPool = new JedisPool(config, host, port, 3000);
        } else {
            jedisPool = new JedisPool(config, host, port, 3000, password);
        }

        return new JedisUtil(jedisPool);
    }
}