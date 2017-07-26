package com.xiaohu.back.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * 1.该java配置类对应的以前的web.xml配置文件里面的内容
 * 【WebApplicationInitializer.java】：这是整个项目的核心。Servlet3.0规范，支持将web.xml相关配置也硬编码到代码中[servlet，filter，listener,等等]，
 * 并由javax.servlet.ServletContainerInitializer的实现类负责在容器启动时进行加载，spring提供了一个实现类SpringServletContainerInitializer
 * （在spring-web包中的org.springframework.web目录）,该类会调用所有org.springframework.web.WebApplicationInitializer实现类的onStartup方法，
 * 将相关的组件注册到服务器；而我们的WebApplicationInitializer继承自AbstractAnnotationConfigDispatcherServletInitializer，
 * 而AbstractAnnotationConfigDispatcherServletInitializer就实现了org.springframework.web.WebApplicationInitializer的onStartup方法，
 * 所以WebApplicationInitializer就是整个项目的关键，我们的整个项目就是通过它来启动。
 * Created by Administrator on 2017/7/12.
 */
@Order(3)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * 应用上下文，除web部分
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //首先要加载的Java配置类，比如将来的缓存配置文件、安全配置文件等
        return new Class[] { RootConfig.class, DatabaseConfig.class};
    }

    /**
     * servlet加载以及spring mvc配置文件加载,web上下文
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringMvcConfig.class};
    }

    /**
     *  DispatcherServlet的映射路径
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "*.shtml" };
    }

    /**
     * spring DispatcherServlet的配置,其它servlet和监听器等需要额外声明，用@Order注解设定启动顺序
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[] {characterEncodingFilter};
    }
}
