package com.connect.demo.main.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@MapperScan(basePackages = "com.connect.demo.main2.dao", sqlSessionTemplateRef = "zentaoSqlSessionTemplate")
public class ZentaoDataSourceConfig  extends BaseSource{


    @Value("${spring.datasource.base2.url:#{null}}")
    private String dbUrl;
    @Value("${spring.datasource.base2.username: #{null}}")
    private String username;
    @Value("${spring.datasource.base2.password:#{null}}")
    private String password;
    @Value("${spring.datasource.base2.driverClassName:#{null}}")
    private String driverClassName;
    @Value("${spring.datasource.base2.initialSize:#{null}}")
    private Integer initialSize;
    @Value("${spring.datasource.base2.minIdle:#{null}}")
    private Integer minIdle;
    @Value("${spring.datasource.base2.maxActive:#{null}}")
    private Integer maxActive;
    @Value("${spring.datasource.base2.maxWait:#{null}}")
    private Integer maxWait;
    @Value("${spring.datasource.base2.timeBetweenEvictionRunsMillis:#{null}}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.base2.minEvictableIdleTimeMillis:#{null}}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.base2.validationQuery:#{null}}")
    private String validationQuery;
    @Value("${spring.datasource.base2.testWhileIdle:#{null}}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.base2.testOnBorrow:#{null}}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.base2.testOnReturn:#{null}}")
    private Boolean testOnReturn;
    @Value("${spring.datasource.base2.poolPreparedStatements:#{null}}")
    private Boolean poolPreparedStatements;
    @Value("${spring.datasource.base2.maxPoolPreparedStatementPerConnectionSize:#{null}}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.base2.filters:#{null}}")
    private String filters;
    @Value("{spring.datasource.base2.connectionProperties:#{null}}")
    private String connectionProperties;





    @Bean(name = "zentaoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base2")
    public DataSource setDataSource() {

        return getDataSource(this.dbUrl,username,password,driverClassName
                ,initialSize,minIdle,maxActive,maxWait,timeBetweenEvictionRunsMillis,minEvictableIdleTimeMillis
                ,validationQuery,testWhileIdle,testOnBorrow,testOnReturn,poolPreparedStatements,maxPoolPreparedStatementPerConnectionSize,connectionProperties);
    }


    @Autowired
    @Bean(name = "zentaoTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("zentaoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Autowired
    @Bean(name = "zentaoSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("zentaoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/main2/*.xml"));
        return bean.getObject();
    }
    @Autowired
    @Bean(name = "zentaoSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("zentaoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}