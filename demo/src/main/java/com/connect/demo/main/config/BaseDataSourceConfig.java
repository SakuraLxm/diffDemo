package com.connect.demo.main.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 *
 *
 * @author sakura  ll
 * qq 286041390
 * @date 2018/8/14 10:31
 */
@Configuration
@MapperScan(basePackages = "com.connect.demo.main.dao", sqlSessionTemplateRef = "baseSqlSessionTemplate")
public class BaseDataSourceConfig extends  BaseSource {
    @Value("${spring.datasource.base.url:#{null}}")
    private String dbUrl;
    @Value("${spring.datasource.base.username: #{null}}")
    private String username;
    @Value("${spring.datasource.base.password:#{null}}")
    private String password;
    @Value("${spring.datasource.base.driverClassName:#{null}}")
    private String driverClassName;
    @Value("${spring.datasource.base.initialSize:#{null}}")
    private Integer initialSize;
    @Value("${spring.datasource.base.minIdle:#{null}}")
    private Integer minIdle;
    @Value("${spring.datasource.base.maxActive:#{null}}")
    private Integer maxActive;
    @Value("${spring.datasource.base.maxWait:#{null}}")
    private Integer maxWait;
    @Value("${spring.datasource.base.timeBetweenEvictionRunsMillis:#{null}}")
    private Integer timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.base.minEvictableIdleTimeMillis:#{null}}")
    private Integer minEvictableIdleTimeMillis;
    @Value("${spring.datasource.base.validationQuery:#{null}}")
    private String validationQuery;
    @Value("${spring.datasource.base.testWhileIdle:#{null}}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.base.testOnBorrow:#{null}}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.base.testOnReturn:#{null}}")
    private Boolean testOnReturn;
    @Value("${spring.datasource.base.poolPreparedStatements:#{null}}")
    private Boolean poolPreparedStatements;
    @Value("${spring.datasource.base.maxPoolPreparedStatementPerConnectionSize:#{null}}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.base.filters:#{null}}")
    private String filters;
    @Value("{spring.datasource.base.connectionProperties:#{null}}")
    private String connectionProperties;

    @Bean(name = "baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.base")
    @Primary
    public DataSource setDataSource() {
        DruidDataSource datasource = getDataSource(this.dbUrl,username,password,driverClassName
        ,initialSize,minIdle,maxActive,maxWait,timeBetweenEvictionRunsMillis,minEvictableIdleTimeMillis
        ,validationQuery,testWhileIdle,testOnBorrow,testOnReturn,poolPreparedStatements,maxPoolPreparedStatementPerConnectionSize,connectionProperties);


        return datasource;
    }
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }



    @Bean(name = "baseTransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(@Qualifier("baseDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/main/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
