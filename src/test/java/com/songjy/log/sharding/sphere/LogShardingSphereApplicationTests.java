package com.songjy.log.sharding.sphere;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class LogShardingSphereApplicationTests {

    @Test
    void contextLoads() throws InterruptedException {
        TimeUnit.SECONDS.sleep(30L);
    }

}
