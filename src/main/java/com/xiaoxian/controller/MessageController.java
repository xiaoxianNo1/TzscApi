package com.xiaoxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xiaoxian.mapper.MessageMapper;
import com.xiaoxian.until.fastjson.Json;
import com.xiaoxian.until.fastjson.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小贤
 * @Date: 2019/3/31/ 0031 9:43
 * @Version 1.0
 */
@Controller
public class MessageController {

    @Autowired
    private final MessageMapper messageMapper;

    public MessageController(MessageMapper messageMapper){
        this.messageMapper=messageMapper;
    }

    @RequestMapping(value = "/getmsglist",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void getMsgList(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request) throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();

        List<Map> msgList=new ArrayList<>();
        Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
        String msguserid=params.get("msguserid")==null?"":params.get("msguserid");
        List<Map> userList=messageMapper.getUserList();
        if(userList!=null){
            for(int i=0;i<userList.size();i++){
                Map userFriendMap=userList.get(i);
                String userfriendid=userFriendMap.get("userid").toString();
                if(!msguserid.equals(userfriendid)){
                    List<Map> friendIdList=messageMapper.getMsgByIdsList(msguserid,userfriendid);
                    if(friendIdList.size()>0){
                        Map<String,List> msgMap=new HashMap<>();
                        msgMap.put(userfriendid,friendIdList);
                        list.add(msgMap);
                    }
                }
            }
        }
        //list = messageMapper.getMsgList(msguserid);
        result=new Result(true,200,list);
        Json.toJson(result,response);
    }

    /**
     * 根据用户id查询消息
     * @param jsonParam
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping( value = "/getmessagebyid",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void getMessageById(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();
        Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map

        String msgfromuserid=params.get("msgfromuserid")==null?"":params.get("msgfromuserid");
        String msgtouserid=params.get("msgtouserid")==null?"":params.get("msgtouserid");
        String msgstatus=params.get("msgstatus")==null?"":params.get("msgstatus");

        list=messageMapper.getMsgById(msgfromuserid,msgtouserid,msgstatus);
        if (list.size()==0||list==null){
            result=new Result(false,200,"没有聊天记录");
        }else if(list.size()>0){
            result=new Result(true,200,"获取消息成功",list);
        }
        Json.toJson(result,response);
    }

    /**
     * 插入消息
     * @param jsonParam
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/insertmessage",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void insertMessage(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();
        Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
        String msgcontent=params.get("msgcontent")==null?"":params.get("msgcontent");
        String msgfromuserid=params.get("msgfromuserid")==null?"":params.get("msgfromuserid");
        String msgtouserid=params.get("msgtouserid")==null?"":params.get("msgtouserid");
        messageMapper.insertmsg(msgcontent,msgfromuserid,msgtouserid);
    }
}
