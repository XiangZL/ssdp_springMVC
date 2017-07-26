package com.xiaohu.back.service.impl;

import com.xiaohu.back.bean.UserBean;
import com.xiaohu.back.dao.mapper.UserMapper;
import com.xiaohu.back.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public List getUserInfo() throws Exception {
        logger.info("----------------启动业务类---");
        List<UserBean> list=userMapper.loadUserByInfo();
        return list;
    }

    @Transactional
    @Override
    public void saveUserInfo(UserBean userBean) throws Exception {
        logger.info("-------新增用户信息start------------");
        userMapper.saveUserInfo(userBean);
        logger.info("---------------新增用户结束----------");
    }
}
