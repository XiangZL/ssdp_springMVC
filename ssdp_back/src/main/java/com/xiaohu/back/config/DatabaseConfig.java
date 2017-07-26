package com.xiaohu.back.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 该文件只适合mybatis的配置，如何集成springJDBC或者hibernate需要按照其原来xml
 * 配置要求重新该java配置文件，但是书写原理一样
 * Created by Administrator on 2017/7/12.
 */
@Configuration
@EnableTransactionManagement//开启注解事务
public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    //连接驱动
    @Value("${jdbc.driver}")
    private String driverClass;
    //数据库连接地址
    @Value("${db.url}")
    private String jdbcUrl;
    //数据库连接用户名
    @Value("${db.username}")
    private String username;
    //数据库连接密码
    @Value("${db.password}")
    private String password;
    //最大连接数
    @Value("${db.minPoolSize}")
    private Integer minPoolSize;
    //最小连接数
    @Value("${db.maxPoolSize}")
    private Integer maxPoolSize;
    //
    @Value("${db.initialPoolSize}")
    private Integer initialPoolSize;

    @Value("${db.maxIdleTime}")
    private Integer maxIdleTime;

    @Value("${db.acquireIncrement}")
    private Integer acquireIncrement;

    /**
     * 数据库连接注入
     * @return
     * @throws PropertyVetoException
     */
    @Bean(name = "dataSource")
    public DataSource dataSource() throws PropertyVetoException {
        logger.info("mysql url:"+this.jdbcUrl);
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(this.driverClass);
        dataSource.setJdbcUrl(this.jdbcUrl);
        dataSource.setUser(this.username);
        dataSource.setPassword(this.password+"");

        dataSource.setMaxPoolSize(this.maxPoolSize);
        dataSource.setMinPoolSize(this.minPoolSize);
        dataSource.setInitialPoolSize(this.initialPoolSize);
        dataSource.setMaxIdleTime(this.maxIdleTime);
        dataSource.setAcquireIncrement(this.acquireIncrement);
        logger.info("dataSource start---------");
        return dataSource;
    }

    /**
     * 事务管理器配置, 使用jdbc事务
     * @param dataSource
     * @return
     */
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager(DataSource dataSource) throws PropertyVetoException {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * mybaits sqlSessionFactory注入
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        //此处可以配置分页插件以及缓存等信息
        sessionFactory.setDataSource(dataSource);
        return sessionFactory.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        SqlSessionTemplate sqlSessionTemplate =new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
        return sqlSessionTemplate;
    }

}
