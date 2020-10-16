package com.songjy.log.sharding.sphere.config;

import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 分表策略
 *
 * @author songjy
 */
public class ShardingTableAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {

    /**
     * 时间格式
     */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public Collection<String> doSharding(Collection<String> tableNames,
                                         ComplexKeysShardingValue<Integer> shardingValues) {

        Map<String, Collection<Integer>> map = shardingValues.getColumnNameAndShardingValuesMap();
        Collection<Integer> orderIdValues = map.get("log_date");
        List<String> shardingSuffix = new ArrayList<>(8);


        for (Integer logDate : orderIdValues) {

            LocalDate localDate = LocalDate.parse(logDate.toString(), formatter);

            String suffix = localDate.getYear() + "_" + localDate.getMonthValue() + "_" + localDate.getDayOfMonth();
            for (String tableName : tableNames) {
                if (tableName.endsWith(suffix)) {
                    shardingSuffix.add(tableName);
                }
            }
        }

        return shardingSuffix;
    }
}
