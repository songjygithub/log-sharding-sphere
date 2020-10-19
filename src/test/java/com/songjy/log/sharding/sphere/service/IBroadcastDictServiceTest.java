package com.songjy.log.sharding.sphere.service;

import cn.hutool.core.lang.Assert;
import com.songjy.log.sharding.sphere.model.BroadcastDict;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author songjy
 * @date 2020-10-19
 */
@SpringBootTest
public class IBroadcastDictServiceTest {

    @Autowired
    private IBroadcastDictService broadcastDictService;

    @Test
    void insertSelectiveTest() {
        BroadcastDict record = new BroadcastDict();
        record.setDictKey("1");
        record.setDictValue("a");
        int r = broadcastDictService.insertSelective(record);
        Assert.isTrue(r > 0);
    }

    @Test
    void selectByPrimaryKeyTest(){
        BroadcastDict dict = broadcastDictService.selectByPrimaryKey(1L);
        Assert.notNull(dict);
    }

    @Test
    void updateByPrimaryKeySelectiveTest(){
        BroadcastDict record = new BroadcastDict();
        record.setId(1L);
        record.setDictValue("aa");
        int r = broadcastDictService.updateByPrimaryKeySelective(record);
        Assert.isTrue(r > 0);
    }
}
