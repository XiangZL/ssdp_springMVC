package com.xiaohu.back.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.util.IntrospectorCleanupListener;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.StandardCharsets;

/**
 * 需要外加载的java配置类；
 * 1.监听器配置加载
 * 2.日志配置文件监听加载
 * 3.路径不一致的编码设置加载
 * Created by Administrator on 2017/7/12.
 */
@Order(1)
public class CommonInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Log4jConfigListener
        //servletContext.setInitParameter("log4jConfigLocation", "classpath:config/properties/log4j.properties");
        //servletContext.addListener(Log4jConfigListener.class);
        //bernate opensession加载

        //与dispatch连接不一致的编码设置
        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", String.valueOf(StandardCharsets.UTF_8));
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        //防止缓存溢出配置
        servletContext.addListener(IntrospectorCleanupListener.class);
        //自定义DemoServlet  exp:
        //DemoServlet demoServlet = new DemoServlet();
        //ServletRegistration.Dynamic dynamic = servletContext.addServlet("demoServlet", demoServlet);
        //dynamic.setLoadOnStartup(2);
        //dynamic.addMapping("/demo_servlet");

        //shrio验证用的过滤器配置等
    }
}
