package com.xiaoxian.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: 小贤
 * @Date: 2019/3/19/ 0019 17:13
 * @Version 1.0
 */

@Mapper
@Repository
public interface UserLoginMapper {
    //获取用户信息
    List<Map> getUserInfo(@Param("uphone")String uphone, @Param("uemail")String uemail,@Param("upassword")String upassword);
    //保存登录日志
    int saveLoginLog (@Param("userid")String userid);
    //添加用户
    int addUser(@Param("username")String username,@Param("upassword")String upassword,@Param("uemail")String uemail,@Param("uphone")String uphone,@Param("usex")String usex);
}
