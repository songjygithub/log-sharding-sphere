package com.songjy.log.sharding.sphere.service;

import cn.hutool.core.lang.Assert;
import com.songjy.log.sharding.sphere.model.LogStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

/**
 * @author songjy
 * @date 2020-10-17
 */
@SpringBootTest
@Slf4j
public class ILogStoreServiceTest {

    @Autowired
    private ILogStoreService logStoreService;

    @Test
    void selectByLogDateTest() {
        List<LogStore> logStores = logStoreService.selectByLogDate(LocalDate.now().minusDays(1L));
        Assert.notEmpty(logStores);
        logStores.forEach(e -> log.info("主键>>{}", e.getId()));
    }

    @Test
    void selectBetweenLogDateTest() {
        List<LogStore> logStores = logStoreService.selectBetweenLogDate(LocalDate.now().minusDays(1L), LocalDate.now());
        Assert.notEmpty(logStores);
        logStores.forEach(e -> log.info("主键>>{}", e.getId()));
    }

}
