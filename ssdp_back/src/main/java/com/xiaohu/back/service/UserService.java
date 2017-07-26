package com.xiaohu.back.service;

import com.xiaohu.back.bean.UserBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface UserService<E> {
    /**
     * 获取用户信息
     * @return
     * @throws Exception
     */
    public List<E> getUserInfo() throws Exception;

    /**
     * 新增用户信息
     * @param userBean
     * @throws Exception
     */
    public void saveUserInfo(UserBean userBean) throws Exception;
}
