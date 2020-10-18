package com.songjy.log.sharding.sphere.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author songjy
 * @date 2020-10-16
 */
@Getter
@Setter
public class LogStoreDto implements Serializable {
    private static final long serialVersionUID = 2205146162576114498L;

    private String source;

    /**
     * 主机名称或IP
     */
    private String host;

    private String path;

    /**
     * 分类
     */
    private String type;

    /**
     * 标签
     */
    private String[] tags;

    /**
     * 日志信息
     */
    private String message;

    /**
     * 类全名
     */
    private String logger;
    /**
     * 日志级别
     */
    private String level;
    /**
     * 线程名
     */
    private String thread;

    @JSONField(name = "@timestamp")
    private Date timestamp;

    /**
     * 类信息
     */
    private Location location;
    /**
     * 错误堆栈
     */
    private String throwable;

}
