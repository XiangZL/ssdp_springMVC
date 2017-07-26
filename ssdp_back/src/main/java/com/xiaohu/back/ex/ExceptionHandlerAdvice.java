package com.xiaohu.back.ex;

import com.xiaohu.back.util.DateConvertEditor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * 全局异常处理
 */
@ControllerAdvice //1 声明一个控制器建言
public class ExceptionHandlerAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = BusinessException.class)
    public ModelAndView exception(Exception exception, WebRequest request) {
        logger.info("Business exception handler:"+exception.getMessage());
        ModelAndView modelAndView = new ModelAndView("pageError");
        modelAndView.addObject("errorMessage", exception.getMessage());
        modelAndView.addObject("ex", exception);
        return modelAndView;
    }

    @ModelAttribute
    public  void  addAtrribute(Model model){
        model.addAttribute("msg","额外信息");
    }

    /**
     * 1.用于界面日期控件到后台的转换
     * 2.通过@InitBinder注解定制WebDataBinder
     * @param webDataBinder
     * @param request
     */
    @InitBinder
    public void  initBinder(WebDataBinder webDataBinder, WebRequest request){
        webDataBinder.registerCustomEditor(Date.class, new DateConvertEditor());
    }

}
