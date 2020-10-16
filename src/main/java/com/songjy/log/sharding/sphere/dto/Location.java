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

    @JSONField(name = "class")
    private String clazz;

    private String method;

    private Integer line;
}
