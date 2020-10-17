package com.songjy.log.sharding.sphere.config;

import com.google.common.collect.Range;
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

        List<String> actualTableNames = new ArrayList<>(8);

        doSharding(tableNames, shardingValues, actualTableNames);

        rangeSharding(tableNames, shardingValues, actualTableNames);


        return actualTableNames;
    }

    /**
     * between
     *
     * @param tableNames       所有表名
     * @param shardingValue    分片值
     * @param actualTableNames 实际物理表名
     */
    private void rangeSharding(Collection<String> tableNames, ComplexKeysShardingValue<Integer> shardingValue, List<String> actualTableNames) {
        Map<String, Range<Integer>> columnNameAndRangeValuesMap = shardingValue.getColumnNameAndRangeValuesMap();
        Range<Integer> range = columnNameAndRangeValuesMap.get("log_date");

        if (null == range) {
            return;
        }

        int lower = range.lowerEndpoint();
        int upper = range.upperEndpoint();

        for (int i = lower; i <= upper; i++) {

            LocalDate localDate = LocalDate.parse(String.valueOf(i), formatter);
            String suffix = localDate.getYear() + "_" + localDate.getMonthValue() + "_" + localDate.getDayOfMonth();

            for (String dbName : tableNames) {
                if (dbName.endsWith(suffix)) {
                    actualTableNames.add(dbName);
                }
            }
        }
    }

    /**
     * =
     *
     * @param tableNames       所有表名
     * @param shardingValues   分片值
     * @param actualTableNames 实际物理表名
     */
    private void doSharding(Collection<String> tableNames, ComplexKeysShardingValue<Integer> shardingValues, List<String> actualTableNames) {
        Map<String, Collection<Integer>> map = shardingValues.getColumnNameAndShardingValuesMap();
        Collection<Integer> orderIdValues = map.get("log_date");

        if (null == orderIdValues || orderIdValues.isEmpty()) {
            return;
        }

        for (Integer logDate : orderIdValues) {

            LocalDate localDate = LocalDate.parse(logDate.toString(), formatter);

            String suffix = localDate.getYear() + "_" + localDate.getMonthValue() + "_" + localDate.getDayOfMonth();
            for (String tableName : tableNames) {
                if (tableName.endsWith(suffix)) {
                    actualTableNames.add(tableName);
                }
            }
        }
    }
}
