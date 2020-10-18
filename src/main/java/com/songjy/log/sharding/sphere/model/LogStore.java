package com.songjy.log.sharding.sphere.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author songjy
 */
@Getter
@Setter
public class LogStore implements Serializable {
    private static final long serialVersionUID = -7467751988618132971L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 日志日期，yyyyMMdd，整型
     */
    private Integer logDate;
    /**
     * 日志时间，hh:mm:ss
     */
    private Date logTime;
    /**
     * 日志来源，即所属应用
     */
    private String logSource;
    /**
     * 日志机器IP
     */
    private String logIp;
    /**
     * 日志打印所在类
     */
    private String clazz;
    /**
     * 日志打印所在方法
     */
    private String method;
    /**
     * 日志打印所在行号
     */
    private Integer line;
    /**
     * 日志内容
     */
    private String message;
    /**
     * 日志级别
     */
    private String level;
    /**
     * 错误堆栈
     */
    private String throwable;
}