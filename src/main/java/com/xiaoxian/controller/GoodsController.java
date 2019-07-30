package com.xiaoxian.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xiaoxian.mapper.GoodsMapper;
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
 * @Date: 2019/3/26/ 0026 11:28
 * @Version 1.0
 */
@Controller
public class GoodsController {

    @Autowired
    GoodsMapper goodsMapper;

    @RequestMapping( value = "/getgoodsinfo",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    private void getGoodsInfo(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();
        Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
        String goodsName=params.get("goodsName")==null?"":params.get("goodsName");
        System.out.println("goodsName"+goodsName);
        list = goodsMapper.selectGoodsInfo(goodsName);
        result= new Result(true,200,"商品信息",list);
        Json.toJson(result,response);
    }

    @RequestMapping( value = "/getgoodsdetailsbygoodsid",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    private void getGoodsDetailsByGoodsId(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception{
        Result result=null;
        List<Map> list=new ArrayList<>();
        Map<String, String> params=JSONObject.parseObject(jsonParam.toJSONString(),new TypeReference<Map<String,String>>(){});//jsonobject转map
        String goodsid=params.get("goodsId")==null?"":params.get("goodsId");
        Map map=goodsMapper.getGoodDetailsById(goodsid);
        result=new Result(true,200,"商品详细信息",map);
        Json.toJson(result,response);
    }

    @RequestMapping( value = "/orderfrom",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    private void generateOrderInformation(@RequestBody JSONObject jsonParam, HttpServletResponse response, HttpServletRequest request)throws Exception {
        Result result = null;
        List<Map> list = new ArrayList<>();
        Map<String, String> params = JSONObject.parseObject(jsonParam.toJSONString(), new TypeReference<Map<String, String>>() {});//jsonobject转map
        String userid=params.get("userid")==null?"":params.get("userid");
        String userid1=params.get("userid1")==null?"":params.get("userid1");
        String name=params.get("name")==null?"":params.get("name");
        String address=params.get("address")==null?"":params.get("address");
        String tel=params.get("tel")==null?"":params.get("tel");
        String email=params.get("email")==null?"":params.get("email");
        String cost=params.get("cost")==null?"":params.get("cost");
        String state=params.get("state")==null?"":params.get("state");
        String send=params.get("send")==null?"":params.get("send");

        String goodsid=params.get("goodsid")==null?"":params.get("goodsid");
        String goodsname=params.get("goodsname")==null?"":params.get("goodsname");
        String goodsprice=params.get("goodsprice")==null?"":params.get("goodsprice");
        String goodsnum=params.get("goodsnum")==null?"":params.get("goodsnum");
        String concost=params.get("concost")==null?"":params.get("concost");
        goodsMapper.GenerateOrderInformation(userid,userid1,name,address,tel,email,cost,state,send);
        List<Map> list1=goodsMapper.getOrderIdInfo(userid,userid1,name,address,tel,email,cost,state,send);
        Map<String,Object> mapOrderId=list1.get(0);
        String orderId=mapOrderId.get("orderid").toString();
        goodsMapper.GenerateOrderDetailInfo(orderId,goodsid,goodsname,goodsprice,goodsnum,concost);
        result=new Result(true,200,orderId);
        Json.toJson(result,response);
    }
}
