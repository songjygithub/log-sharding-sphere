package com.songjy.log.sharding.sphere.config;

import com.google.common.collect.Range;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 分库策略
 *
 * @author songjy
 * @date 2020-10-17
 */
public class ShardingDbAlgorithm implements ComplexKeysShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> dbNames, ComplexKeysShardingValue<Integer> shardingValue) {

        List<String> actualDbNames = new ArrayList<>(4);

        doSharding(dbNames, shardingValue, actualDbNames);

        rangeSharding(dbNames, shardingValue, actualDbNames);

        return actualDbNames;
    }

    /**
     * between
     *
     * @param dbNames       所有数据库别名
     * @param shardingValue 分片值
     * @param actualDbNames 实际库别名
     */
    private void rangeSharding(Collection<String> dbNames, ComplexKeysShardingValue<Integer> shardingValue, List<String> actualDbNames) {
        Map<String, Range<Integer>> columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        Range<Integer> range = columnNameAndRangeValuesMap.get("log_date");

        if (null == range) {
            return;
        }

        int lower = range.lowerEndpoint();
        int upper = range.upperEndpoint();


        for (int i = lower; i <= upper; i++) {

            int mod = i % 2;
            for (String dbName : dbNames) {
                if (dbName.endsWith(String.valueOf(mod))) {
                    actualDbNames.add(dbName);
                }
            }
        }
    }

    /**
     * =
     *
     * @param dbNames       所有数据库别名
     * @param shardingValue 分片值
     * @param actualDbNames 实际库别名
     */
    private void doSharding(Collection<String> dbNames, ComplexKeysShardingValue<Integer> shardingValue, List<String> actualDbNames) {
        Map<String, Collection<Integer>> map = shardingValue.getColumnNameAndShardingValuesMap();
        Collection<Integer> orderIdValues = map.get("log_date");

        if (null == orderIdValues || orderIdValues.isEmpty()) {
            return;
        }

        for (Integer logDate : orderIdValues) {

            int mod = logDate % 2;

            for (String dbName : dbNames) {
                if (dbName.endsWith(String.valueOf(mod))) {
                    actualDbNames.add(dbName);
                }
            }
        }
    }
}
