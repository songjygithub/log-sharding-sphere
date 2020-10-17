package com.songjy.log.sharding.sphere.service;

import cn.hutool.core.lang.Assert;
import com.songjy.log.sharding.sphere.model.LogStore;
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
public class ILogStoreServiceTest {

    @Autowired
    private ILogStoreService logStoreService;

    @Test
    void selectByLogDateTest() {
        List<LogStore> logStores = logStoreService.selectByLogDate(LocalDate.now().minusDays(1L));
        Assert.notEmpty(logStores);
        logStores.forEach(e -> System.out.println(e.getId()));
    }

    @Test
    void selectBetweenLogDateTest() {
        List<LogStore> logStores = logStoreService.selectBetweenLogDate(LocalDate.now().minusDays(1), LocalDate.now());
        Assert.notEmpty(logStores);
        logStores.forEach(e -> System.out.println(e.getId()));
    }

}
