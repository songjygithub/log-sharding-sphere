package com.songjy.log.sharding.sphere.mapper;

import com.songjy.log.sharding.sphere.model.LogStore;

/**
 * @author songjy
 */
public interface LogStoreMapper {
    /**
     * @param id 主键
     * @return 1 or 0
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 添加记录
     *
     * @param record 记录
     * @return 1 or 0
     */
    int insertSelective(LogStore record);

    /**
     * 根据主键ID查询单个记录
     *
     * @param id 主键
     * @return 记录
     */
    LogStore selectByPrimaryKey(Long id);
}