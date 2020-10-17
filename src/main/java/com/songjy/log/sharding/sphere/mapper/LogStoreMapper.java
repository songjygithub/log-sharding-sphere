package com.songjy.log.sharding.sphere.mapper;

import com.songjy.log.sharding.sphere.model.LogStore;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author songjy
 */
public interface LogStoreMapper {
    /**
     * 添加记录
     *
     * @param record 记录
     * @return 1 or 0
     */
    int insertSelective(LogStore record);

    /**
     * 根据指定日期查询记录
     *
     * @param logDate 日期：yyyyMMdd
     * @return 记录
     */
    List<LogStore> selectByLogDate(int logDate);

    /**
     * 根据时间范围查询记录
     *
     * @param startDate 开始时间：yyyyMMdd
     * @param endDate   结束时间：yyyyMMdd
     * @return 记录
     */
    List<LogStore> selectBetweenLogDate(@Param("startDate") int startDate, @Param("endDate") int endDate);
}