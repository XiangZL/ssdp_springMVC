package com.xiaohu.back.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 基础配置的java配置类，相对于以前声明是xml配置的application-context.xml
 */
@Configuration//标志java配置文件
@PropertySource("classpath:config.properties")//导入属性资源文件
@EnableAspectJAutoProxy//相当于xml中的<aop:aspectj-autoproxy/>
//开启扫描@Service以及@Repository bean
@ComponentScan(basePackages = {"com.xiaohu.**.dao","com.xiaohu.**.dao.impl","com.xiaohu.**.service","com.xiaohu.**.service.impl"}, excludeFilters = @ComponentScan.Filter(classes = Controller.class))
//mybatis中的mapper扫描
@MapperScan("com.xiaohu.**.dao.mapper")
public class RootConfig {
    //上面导入的属性文件中的属性会 注入到 Environment 中
    @Resource
    private Environment env;
    /**
     * 资源文件获取Bean注入
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    /**
     * 任务调度注入
     * @return
     */
//    @Bean(name="schedulerFactoryBean")
//    public SchedulerFactoryBean schedulerFactoryBean(){
//        return new SchedulerFactoryBean();
//    }
}
