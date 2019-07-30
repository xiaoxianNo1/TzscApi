package com.xiaoxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xiaoxian.mapper.UserLoginMapper;
import com.xiaoxian.until.GetIp;
import com.xiaoxian.until.fastjson.Json;
import com.xiaoxian.until.fastjson.Result;
import com.xiaoxian.until.jmjm.getUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小贤
 * @Date: 2019/3/19/ 0019 17:12
 * @Version 1.0
 */
@Controller
public class UserLoginController {
    @Autowired
    private final UserLoginMapper userLoginMapper;


    @Autowired
    public UserLoginController(UserLoginMapper userLoginMapper){
        this.userLoginMapper=userLoginMapper;
    }

    //用户登录
    @RequestMapping( value = "/userlogin",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void UserLogin(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();
        //if(GetIp.getIpAddr(request).equals(GetIp.szip())){
        if(true){
            Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
            String uemail=params.get("uemail")==null?"":params.get("uemail");
            String uphone=params.get("uphone")==null?"":params.get("uphone");
            String upassword=params.get("upassword")==null?"":getUrl.encrypt(params.get("upassword"),0);//0为解密；1为加密

            if(uemail.isEmpty()&&uphone.isEmpty()){
                result = new Result(false,200,"手机号和邮箱不能同时为空");
            }else {
                list=userLoginMapper.getUserInfo(uphone,uemail,"");
            }

            if(list.size()==0){
                result = new Result(false,200,"用户不存在");
            }else if(list.size()>1){
                result = new Result(false,200,"用户已经被重复注册");
            }else if(list.size()==1) {
                Map map=list.get(0);
                String userMima=getUrl.encrypt(map.get("upassword").toString(),0);
                if(userMima.equals(upassword)){
                    result=new Result(true,200,"登录成功",map);
                    userLoginMapper.saveLoginLog(map.get("userid").toString());
                }else {
                    result=new Result(false,200,"密码错误");
                }
            }
        }else {
            result=new Result(false,200,"您没有访问权限");
        }
        Json.toJson(result,response);
    }

    //用户注册
    @RequestMapping( value = "/userregister",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void UserRegister(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();
        //if(GetIp.getIpAddr(request).equals(GetIp.szip())){
        if(true){
            Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
            String username=params.get("username")==null?"":params.get("username");
            String upassword=params.get("upassword")==null?"":params.get("upassword");
            String uemail=params.get("uemail")==null?"":params.get("uemail");
            String uphone=params.get("uphone")==null?"":params.get("uphone");
            String usex=params.get("usex")==null?"":params.get("usex");
            System.out.println(upassword);
            userLoginMapper.addUser(username,upassword,uemail,uphone,usex);
            List<Map> userList=userLoginMapper.getUserInfo(uphone,uemail,upassword);
            result=new Result(true,200,"注册成功",userList);
        }else {
            result=new Result(false,200,"您没有访问权限");
        }
        Json.toJson(result,response);
    }
}
