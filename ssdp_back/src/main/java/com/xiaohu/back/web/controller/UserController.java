package com.xiaohu.back.web.controller;

import com.xiaohu.back.bean.UserBean;
import com.xiaohu.back.ex.BusinessException;
import com.xiaohu.back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    /**
     * spring mvc全注解测试框架
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showHomePage() {
        try {
            logger.info("load user start----------------");
            List<UserBean> list=userService.getUserInfo();
            logger.info("总记录数"+list.size());
            logger.info("load user end----------------");
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
        }
        return "user_info";
    }

    @RequestMapping(value = "/saveInfo",method = RequestMethod.GET)
    public String saveInfo(@ModelAttribute("msg") String msg) throws BusinessException {
        logger.info("-------------save user info----");
        try {
            userService.saveUserInfo(this.getUserBean());
        } catch (Exception e) {
            throw new BusinessException("非常抱歉，参数错误来自注解的统一异常信息以及@ModelAttribute的"+msg);
        }
        return "user_info";
    }

    @RequestMapping(value = "/saveInfo1",method = RequestMethod.GET)
    public String saveInfo1() throws Exception {
        logger.info("-------------save user info----");
        try {
            userService.saveUserInfo(this.getUserBean());
        } catch (Exception e) {
            throw new Exception("非常抱歉，参数错误,来自java配置Bean的统一异常信息");
        }
        return "user_info";
    }

    private UserBean getUserBean(){
        UserBean userBean = new UserBean();
        userBean.setFid("xx124");
        userBean.setLoginId("admin123");
        userBean.setPassWord("123445");
        userBean.setUserName("admin123");
        return  userBean;
    }
}
