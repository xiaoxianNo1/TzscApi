package com.xiaoxian.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: 小贤
 * @Date: 2019/3/26/ 0026 11:28
 * @Version 1.0
 */
@Mapper
@Repository
public interface GoodsMapper {
    List<Map> selectGoodsInfo(@Param("goodsname")String goodsname);

    //根据goodsid获取商品信息
    Map getGoodDetailsById(@Param("goodsid")String goodsid);

    /**
     * 生成订单信息
     */
    void GenerateOrderInformation(
            @Param("userid")String userid,
            @Param("userid1")String userid1,
            @Param("receivername")String name,
            @Param("address")String address,
            @Param("tel")String tel,
            @Param("email")String email,
            @Param("cost")String cost,
            @Param("state")String state,
            @Param("send")String send
    );

    /**
     *获取订单主键
     */
    List<Map> getOrderIdInfo(
            @Param("userid")String userid,
            @Param("userid1")String userid1,
            @Param("receivername")String name,
            @Param("address")String address,
            @Param("tel")String tel,
            @Param("email")String email,
            @Param("cost")String cost,
            @Param("state")String state,
            @Param("send")String send
    );

    /**
     * 生成订单详情信息
     */
    void GenerateOrderDetailInfo(
            @Param("orderid")String orderid,
            @Param("goodsid")String goodsid,
            @Param("goodsname")String goodsname,
            @Param("goodsprice")String goodsprice,
            @Param("goodsnum")String goodsnum,
            @Param("concost")String concost
    );
}
