package com.connect.demo.main.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author sakura  ll
 * qq 286041390
 * @date 2018/8/14 15:07
 */
public class BaseSource {

    protected DruidDataSource getDataSource(String url, String username, String password, String driverClassName
            , Integer initialSize, Integer minIdle, Integer maxActive
            , Integer maxWait, Integer timeBetweenEvictionRunsMillis, Integer minEvictableIdleTimeMillis
            , String validationQuery, Boolean testWhileIdle, Boolean testOnBorrow, Boolean testOnReturn,
                                            Boolean poolPreparedStatements, Integer maxPoolPreparedStatementPerConnectionSize, String connectionProperties

    ) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        if (initialSize != null) {
            datasource.setInitialSize(initialSize);
        }
        if (minIdle != null) {
            datasource.setMinIdle(minIdle);
        }
        if (maxActive != null) {
            datasource.setMaxActive(maxActive);
        }
        if (maxWait != null) {
            datasource.setMaxWait(maxWait);
        }
        if (timeBetweenEvictionRunsMillis != null) {
            datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        }
        if (minEvictableIdleTimeMillis != null) {
            datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        }
        if (validationQuery != null) {
            datasource.setValidationQuery(validationQuery);
        }
        if (testWhileIdle != null) {
            datasource.setTestWhileIdle(testWhileIdle);
        }
        if (testOnBorrow != null) {
            datasource.setTestOnBorrow(testOnBorrow);
        }
        if (testOnReturn != null) {
            datasource.setTestOnReturn(testOnReturn);
        }
        if (poolPreparedStatements != null) {
            datasource.setPoolPreparedStatements(poolPreparedStatements);
        }
        if (maxPoolPreparedStatementPerConnectionSize != null) {
            datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        }

        if (connectionProperties != null) {
            datasource.setConnectionProperties(connectionProperties);
        }

        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(wallFilter());
        datasource.setProxyFilters(filters);
        return datasource;

    }

    @Bean
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        statFilter.setSlowSqlMillis(1000);

        return statFilter;
    }

    @Bean
    public WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();

        //允许执行多条SQL
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);

        return wallFilter;
    }
}
