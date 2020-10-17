package com.songjy.log.sharding.sphere.service;

import com.songjy.log.sharding.sphere.model.LogStore;

import java.time.LocalDate;
import java.util.List;

/**
 * @author songjy
 */
public interface ILogStoreService {

    /**
     * 根据指定日期查询记录
     *
     * @param logDate 日期
     * @return 记录
     */
    List<LogStore> selectByLogDate(LocalDate logDate);

    /**
     * 根据主键ID查询单个记录
     *
     * @param startDate 开始时间：yyyyMMdd
     * @param endDate   结束时间：yyyyMMdd
     * @return 记录
     */
    List<LogStore> selectBetweenLogDate(LocalDate startDate, LocalDate endDate);
}
