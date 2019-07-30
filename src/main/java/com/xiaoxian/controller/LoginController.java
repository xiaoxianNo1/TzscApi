package com.xiaoxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xiaoxian.mapper.LoginMapper;
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

@Controller
public class LoginController {
    @Autowired
    LoginMapper loginMapper;

    /**
     * 用户登录
     * @param jsonParam
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping( value = "/login",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void login(@RequestBody JSONObject jsonParam, HttpServletResponse response,HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> listUser=new ArrayList<>();

        if(GetIp.getIpAddr(request).equals(GetIp.szip())){
            Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
            String loginSjh=params.get("sjh")==null?"":params.get("sjh");
            String loginEmaill=params.get("emaill")==null?"":params.get("emaill");
            String loginMima= params.get("mima")==null?"": getUrl.encrypt(params.get("mima"),0);//0为解密；1为加密
            //System.out.println(loginMima);
            if(loginSjh.isEmpty()&&loginEmaill.isEmpty()){
                result = new Result(false,200,"手机号和邮箱不能同时为空");
            }else{
                listUser=loginMapper.getUser(loginSjh,loginEmaill);
            }

            if(listUser.size()==0){
                result = new Result(false,200,"用户不存在");
            }else if(listUser.size()>1){
                result = new Result(false,200,"用户已经被重复注册");
            }else if(listUser.size()==1) {
                Map map=listUser.get(0);
                String userMima=getUrl.encrypt(map.get("mima").toString(),0);
                if(userMima.equals(loginMima)){
                    result=new Result(true,200,"登录成功",map);
                    //日志记录
                    loginMapper.saveLoginlog(map.get("uuid").toString(),getUrl.getDatetime());
                }else {
                    result=new Result(false,200,"密码错误");
                }
            }

        }else {
            result=new Result(false,200,"您没有访问权限");
        }
        Json.toJson(result,response);
    }

    /**
     * 用户注册
     * @param jsonParam
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void register(@RequestBody JSONObject jsonParam, HttpServletResponse response,HttpServletRequest request)throws Exception{
        Result result=null;
        if(GetIp.getIpAddr(request).equals(GetIp.szip())){
            Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
            String nc=params.get("nc")==null?"":params.get("nc");
            String sjh=params.get("sjh")==null?"":params.get("sjh");
            String mima=params.get("mima")==null?"":params.get("mima");
            String email=params.get("email")==null?"":params.get("email");
            if(nc==""){
                result = new Result(false,200,"昵称不为空");
            }else if(sjh==""){
                result = new Result(false,200,"手机号不能为空");
            }else if(mima==""){
                result = new Result(false,200,"密码不能为空");
            }else if(email==""){
                result = new Result(false,200,"邮箱不能为空");
            }else {
                int success=loginMapper.addUser(nc,sjh,mima,email,getUrl.getDatetime());
                //System.out.println("success"+success);
                result = new Result(true,200,"添加成功",success);
            }
        }else {
            result=new Result(false,200,"您没有访问权限");
        }
        Json.toJson(result,response);
    }
}
