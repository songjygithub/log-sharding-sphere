package com.songjy.log.sharding.sphere.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author songjy
 * @date 2020-10-16
 */
@Setter
@Getter
public class Location implements Serializable {
    private static final long serialVersionUID = 216853696766072101L;

    /**
     * 类全名
     */
    @JSONField(name = "class")
    private String clazz;

    /**
     * 方法名
     */
    private String method;

    /**
     * 日志打印所在行号
     */
    private Integer line;
}
