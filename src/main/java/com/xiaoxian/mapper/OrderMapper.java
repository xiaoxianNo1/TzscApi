package com.xiaoxian.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: 小贤
 * @Date: 2019/4/8/ 0008 14:21
 * @Version 1.0
 */
@Mapper
@Repository
public interface OrderMapper {
    List<Map> getOrderList(@Param("userid")String userid);

    List<Map> getOrderListForSell(@Param("userid1")String userid);
}
