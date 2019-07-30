package com.xiaoxian.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface LoginMapper {
    //获取用户信息
    public List<Map> getUser(@Param("SJH")String sjh,@Param("EMAIL")String email);
    //新增用户
    public int addUser(@Param("NC")String nc,@Param("SJH")String sjh,@Param("MIMA")String mima,@Param("EMAIL")String email,@Param("ZCRQ")String zcsj);
    //保存登录日志
    public int saveLoginlog(@Param("DLRID")String dlrid,@Param("DLRQ")String dlrq);
}
