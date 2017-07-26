package com.xiaohu.back.dao.mapper;

import com.xiaohu.back.bean.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
public interface UserMapper {
    /**
     * 获取用户列表信息
     * @return
     */
    @Select(value="SELECT FID, LOGIN_ID , USER_NAME , LOGIN_PWD  FROM  USER_TEST ")
    public List<UserBean> loadUserByInfo();

    /**信息
     * @param userBean
     */
    @Insert(value ="insert into USER_TEST(FID,USER_name,login_Id,login_pwd) VALUES (#{fid},#{userName},#{loginId},#{passWord})")
    public void saveUserInfo(UserBean userBean);
}
