package com.xiaoxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xiaoxian.mapper.OrderMapper;
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
import java.util.List;
import java.util.Map;

/**
 * @Author: 小贤
 * @Date: 2019/4/8/ 0008 14:20
 * @Version 1.0
 */
@Controller
public class OrderController {
    @Autowired
    OrderMapper orderMapper;

    @RequestMapping( value = "/getorders",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    private void getorders(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();
        Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
        String TAG=params.get("lastTAG")==null?"":params.get("lastTAG");
        if(TAG.equals("buy")){
            String userid=params.get("userid")==null?"":params.get("userid");
            list=orderMapper.getOrderList(userid);
        }else if(TAG.equals("sell")){
            String userid=params.get("userid")==null?"":params.get("userid");
            list=orderMapper.getOrderListForSell(userid);
        }

        result=new Result(true,200,"获取用户订单信息",list);
        Json.toJson(result,response);
    }
}
