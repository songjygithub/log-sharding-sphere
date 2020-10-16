package com.songjy.log.sharding.sphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author songjy
 */
@SpringBootApplication
@MapperScan("com.songjy.log.sharding.sphere.mapper")
public class LogShardingSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogShardingSphereApplication.class, args);
    }

}
