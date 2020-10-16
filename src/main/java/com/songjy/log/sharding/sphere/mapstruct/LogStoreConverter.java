package com.songjy.log.sharding.sphere.mapstruct;


import com.songjy.log.sharding.sphere.dto.LogStoreDto;
import com.songjy.log.sharding.sphere.model.LogStore;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author songjy
 */
@org.mapstruct.Mapper
public interface LogStoreConverter {

    LogStoreConverter INSTANCE = Mappers.getMapper(LogStoreConverter.class);

    /**
     * 对象转换
     *
     * @param logStoreDto LogStoreDto
     * @return LogStore
     */
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "logDate", ignore = true),
            @Mapping(target = "logTime", ignore = true),
            @Mapping(source = "source", target = "logSource"),
            @Mapping(source = "host", target = "logIp"),
            @Mapping(source = "location.clazz", target = "clazz"),
            @Mapping(source = "location.method", target = "method"),
            @Mapping(source = "location.line", target = "line"),
    })
    LogStore toDO(LogStoreDto logStoreDto);

}
