package com.songjy.log.sharding.sphere.service;

import com.songjy.log.sharding.sphere.mapper.BroadcastDictMapper;
import com.songjy.log.sharding.sphere.model.BroadcastDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author songjy
 * @date 2020-10-19
 */
@Service
public class BroadcastDictServiceImpl implements IBroadcastDictService {

    @Autowired
    private BroadcastDictMapper broadcastDictMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return broadcastDictMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(BroadcastDict record) {
        return broadcastDictMapper.insertSelective(record);
    }

    @Override
    public BroadcastDict selectByPrimaryKey(Long id) {
        return broadcastDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(BroadcastDict record) {
        return broadcastDictMapper.updateByPrimaryKeySelective(record);
    }
}
