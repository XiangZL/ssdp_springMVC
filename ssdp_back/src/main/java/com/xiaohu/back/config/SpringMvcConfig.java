package com.xiaohu.back.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * Created by Administrator on 2017/7/6.
 * 该文件作为java配置文件，主要完成的工作是替代以前spring—mvc-servlet.xml里面的相关配置；
 * 1.视图解析配置
 * 2.全局异常配置
 * 3.拦截器配置
 * 4.json转换配置
 * 5.国际化资源配置
 * 只要以前spring—mvc-servlet.xml里面要配置的东西都在此处完成
 *
 */
@Configurable
@EnableWebMvc// 启用 SpringMVC ，相当于 xml中的 <mvc:annotation-driven/>
@ComponentScan(basePackages = {"com.xiaohu.**.web.controller","com.xiaohu.**.ex"},includeFilters = {@ComponentScan.Filter(classes = Controller.class),
        @ComponentScan.Filter(classes =ControllerAdvice.class)},useDefaultFilters = false)
//可以配置多个，比如此处将service、dao统一扫描
public class SpringMvcConfig extends WebMvcConfigurerAdapter {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String DEFAULT_ERROR_PAGE="/error/pageError";//自定义默认错误页面
    private final String DEFAULT_ERROR="error";//默认错误页面
    private final String DEFAULT_EXCEPTION="ex";//默认异常页面
    /**
     * 资源文件加载以及过滤规则设置
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        super.addResourceHandlers(registry);
    }

    /**
     * 设置由 web容器处理静态资源 ，相当于 xml中的<mvc:default-servlet-handler/>
     * @param configurer
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    /**
     * 拦截器加载配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("addInterceptors start");
        super.addInterceptors(registry);
        logger.info("addInterceptors end");
    }

    /**
     * 服务启动自动跳转页面配置
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //通过Controllers转发至index页面
        registry.addViewController("/index").setViewName("/index");
        //可以在此处全局配合文件上传自动跳转至该页面
        //registry.addViewController("/toUpload").setViewName("/upload");
    }

    /**
     * 路径参数配置
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //通过重写该方法来限制不可以忽略路径上点（.)后面的参数
        configurer.setUseRegisteredSuffixPatternMatch(false);
    }

    /**
     * 自定义HttpMessageConverter配置
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        logger.info("json文件处理start");
        StringHttpMessageConverter stringHttpMessageConverter =new StringHttpMessageConverter();
        new ArrayList<MediaType>().add(MediaType.valueOf("text/html;charset=UTF-8"));
        List<MediaType> list =new ArrayList<MediaType>();
        list.add(MediaType.valueOf("text/html;charset=UTF-8"));
        stringHttpMessageConverter.setSupportedMediaTypes(list);
        converters.add(stringHttpMessageConverter);
        //FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //阿里的json包fastjson.1.2.18.jar
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //继续使用上面的list，因为此处也要对json对象视图做设置
        list.add(MediaType.valueOf("application/json;charset=UTF-8"));
        jackson2HttpMessageConverter.setSupportedMediaTypes(list);
        converters.add(jackson2HttpMessageConverter);
        converters.add( new FormHttpMessageConverter());
        super.extendMessageConverters(converters);
        logger.info("json文件处理end");
    }

    /**
     * Spring MVC处理异常有3种方式：
     * 1.使用Spring MVC提供的简单异常处理器SimpleMappingExceptionResolver；如该该方法通过Bean注入即可
     * 2.实现spring的异常处理接口HandlerExceptionResovler自定义自己的异常信息处理器；然后通过Bean注入即可
     * 3.使用@ExceptionHandler注解实现异常处理,如案例中注解异常处理方法
     * @return
     */
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        //定义默认的异常处理页面，当该异常类型的注册时使用
        simpleMappingExceptionResolver.setDefaultErrorView(this.DEFAULT_ERROR);
        //定义异常处理页面用来获取异常信息的变量名，默认名为exception
        simpleMappingExceptionResolver.setExceptionAttribute(this.DEFAULT_EXCEPTION);
        //自定义需要特殊处理的异常，用类名或完全路径名作为key，异常也页名作为值
        //Properties properties =new Properties();
        //自定义异常
        //properties.put("com.xiaohu.springx.exception.BusinessException","error-business");
        //properties.put("com.xiaohu.springx.exception.ParameterException","error-parameter");
        //simpleMappingExceptionResolver.setExceptionMappings(properties);
        //通过code来代表异常返回情况处理情况
        Properties properties1 =new Properties();
        properties1.put("error/500",500);
        properties1.put("error/404",404);
        properties1.put("error/401",401);
        properties1.put("error/403",403);
        //simpleMappingExceptionResolver.setStatusCodes(properties1);
        simpleMappingExceptionResolver.setDefaultStatusCode(400);
        //定义需要特殊处理的异常，用类名或完全路径名作为key，异常页名作为值 -->
        Properties properties2 =new Properties();
        properties2.put("org.springframework.validation.BindException",this.DEFAULT_ERROR_PAGE);
        properties2.put("java.sql.SQLException",this.DEFAULT_ERROR_PAGE);
        properties2.put("org.springframework.web.bind.ServletRequestBindingException",this.DEFAULT_ERROR_PAGE);
        properties2.put("java.lang.IllegalArgumentException",this.DEFAULT_ERROR_PAGE);
        properties2.put("java.lang.Exception",this.DEFAULT_ERROR_PAGE);
        simpleMappingExceptionResolver.setExceptionMappings(properties2);

        //还有一种处理异常的方式；直接在需要输出异常的地方通过注解实现； 使用@ExceptionHandler注解实现异常处理
        //个人不愿意使用，代码侵入严重
        return simpleMappingExceptionResolver;
    }

    /**
     *  注册servlet适配器
     *  1、只需要在自定义的servlet上用@Controller("映射路径")标注即可
     *  2、这里必须明确声明，因为spring默认没有初始化该适配器
     * @return
     */
    @Bean
    public HandlerAdapter servletHandlerAdapter(){
        logger.info("HandlerAdapter");
        return new SimpleServletHandlerAdapter();
    }
    /**
     * 文件上传配置
     * @return
     */
    @Bean(name="multipartResolver")
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        //100M，这个数字应该可以在资源文件中配置
        commonsMultipartResolver.setMaxUploadSize(100*1024*1024);
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        return  commonsMultipartResolver;
    }

    /**
     * 国际化资源引入
     * @return
     */
    @Bean
    public MessageSource resourceBundleMessageSource(){
        logger.info("注册资源：MessageSource");
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("i18n/messages");
        return resourceBundleMessageSource;
    }
    /**
     * jsp 解析视图配置
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver(){
        logger.info("视图解析方法：viewResolver");
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/WEB-INF/view/");
        internalResourceViewResolver.setSuffix(".jsp");
        internalResourceViewResolver.setViewClass(JstlView.class);
        return  internalResourceViewResolver;
    }


}
