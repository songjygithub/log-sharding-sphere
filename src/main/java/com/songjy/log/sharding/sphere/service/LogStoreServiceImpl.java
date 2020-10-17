package com.songjy.log.sharding.sphere.service;

import com.alibaba.fastjson.JSON;
import com.songjy.log.sharding.sphere.commons.AsyncManager;
import com.songjy.log.sharding.sphere.config.JedisUtil;
import com.songjy.log.sharding.sphere.dto.LogStoreDto;
import com.songjy.log.sharding.sphere.mapper.LogStoreMapper;
import com.songjy.log.sharding.sphere.mapstruct.LogStoreConverter;
import com.songjy.log.sharding.sphere.model.LogStore;
import com.songjy.log.sharding.sphere.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author songjy
 * @date 2020-10-16
 */
@Service
@Slf4j
public class LogStoreServiceImpl implements ILogStoreService, InitializingBean {
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private LogStoreMapper logStoreMapper;

    @Override
    public void afterPropertiesSet() {
        handleLog();
    }

    /**
     * 处理日志入库
     */
    public void handleLog() {
        AsyncManager.getInstance().execute(() -> {
            try {
                while (System.currentTimeMillis() > Integer.MIN_VALUE) {
                    /*Redis队列中取出日志(JSON)，*/
                    List<String> brpop = jedisUtil.brpop(SystemUtils.USER_NAME);
                    String json = brpop.get(1);
                    LogStoreDto logStoreDto = JSON.parseObject(json, LogStoreDto.class);
                    LocalDateTime localDateTime = DateUtils.toLocalDateTime(logStoreDto.getTimestamp());
                    LogStore logStore = LogStoreConverter.INSTANCE.toDO(logStoreDto);
                    /*此处特殊处理，日期转成整型:yyyyMMdd*/
                    logStore.setLogDate(Integer.parseInt(StringUtils.remove(localDateTime.toLocalDate().toString(), '-')));
                    logStore.setLogTime(logStoreDto.getTimestamp());
                    logStoreMapper.insertSelective(logStore);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public List<LogStore> selectByLogDate(LocalDate logDate) {

        logDate = null == logDate ? LocalDate.now() : logDate;

        return logStoreMapper.selectByLogDate(Integer.parseInt(StringUtils.remove(logDate.toString(), '-')));
    }

    @Override
    public List<LogStore> selectBetweenLogDate(LocalDate startDate, LocalDate endDate) {

        startDate = null == startDate ? LocalDate.now() : startDate;
        endDate = null == endDate ? LocalDate.now() : endDate;

        int start = Integer.parseInt(StringUtils.remove(startDate.toString(), '-'));
        int end = Integer.parseInt(StringUtils.remove(endDate.toString(), '-'));

        return logStoreMapper.selectBetweenLogDate(start, end);
    }
}
