package com.xiaoxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.xiaoxian.mapper.HelloMapper;
import com.xiaoxian.until.fastjson.Json;
import com.xiaoxian.until.fastjson.RespCode;
import com.xiaoxian.until.fastjson.RespEntity;
import com.xiaoxian.until.fastjson.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class HelloController {
    @Autowired
    HelloMapper helloMapper;


    @RequestMapping("/Hello")
    @ResponseBody
    public String Hello(){
        return "Hello";
    }


    @RequestMapping("/SelectHello")
    @ResponseBody
    public String SelectHello(){
        //List<Map> list =helloMapper.selectHello("测试");
        Map map=helloMapper.selectHello("测试");
        return map.toString();
    }

    @RequestMapping( value = "/JsonHello",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void JsonHello(@RequestBody JSONObject jsonParam,HttpServletResponse response)throws Exception{
        //System.out.println(jsonParam.toString());

        Map map=helloMapper.selectHello("测试");
        Result result = new Result(true,200,"成功",map);
        String json=Json.toJson(result,response);
        //System.out.println(""+json);
    }

    /*@RequestMapping("/JsonHello")
    @ResponseBody
    public void JsonHello(HttpServletResponse response)throws Exception{
        //Body body=requ
        Map map=helloMapper.selectHello("测试");
        Result result = new Result(true,200,"成功",map);
        String json=Json.toJson(result,response);
        System.out.println(""+json);
    }*/

}
