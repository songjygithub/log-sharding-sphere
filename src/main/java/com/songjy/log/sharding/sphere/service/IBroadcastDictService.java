package com.songjy.log.sharding.sphere.service;

import com.songjy.log.sharding.sphere.model.BroadcastDict;

/**
 * @author songjy
 */
public interface IBroadcastDictService {
    /**
     * 根据主键删除记录
     *
     * @param id 主键
     * @return 1 or 0
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据
     *
     * @param record 记录
     * @return 1 or 0
     */
    int insertSelective(BroadcastDict record);

    /**
     * 根据主键查询记录
     *
     * @param id 主键
     * @return 记录
     */
    BroadcastDict selectByPrimaryKey(Long id);

    /**
     * 根据主键修改记录
     *
     * @param record 记录
     * @return 1 or 0
     */
    int updateByPrimaryKeySelective(BroadcastDict record);
}
