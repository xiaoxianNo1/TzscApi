package com.xiaoxian.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: 小贤
 * @Date: 2019/3/31/ 0031 9:17
 * @Version 1.0
 */
@Mapper
@Repository
public interface MessageMapper {

    List<Map> getUserList();

    List<Map> getMsgByIdsList(@Param("userloginid") String userloginid,@Param("userfriendid")String userfriendid);

    List<Map> getMsgList(@Param("msguserid")String msguserid);
    /**
     * 根据接收ID 发送ID 获取消息List
     * @param msgfromuserid
     * @param msgtouserid
     * @return list
     */
    List<Map> getMsgById(@Param("msgfromuserid")String msgfromuserid,@Param("msgtouserid")String msgtouserid,@Param("msgstatus")String msgstatus);
    void insertmsg(@Param("msgcontent")String msgcontent,@Param("msgfromuserid")String msgfromuserid,@Param("msgtouserid")String msgtouserid);
}
