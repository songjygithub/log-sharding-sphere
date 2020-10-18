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

    private String host;

    private String path;

    private String type;

    private String[] tags;

    private String message;

    private String logger;

    private String level;

    private String thread;

    @JSONField(name = "@timestamp")
    private Date timestamp;

    private Location location;
    /**
     * 错误堆栈
     */
    private String throwable;

}
