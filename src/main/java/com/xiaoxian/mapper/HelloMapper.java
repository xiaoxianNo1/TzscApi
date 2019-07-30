package com.xiaoxian.mapper;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface HelloMapper {
    public Map selectHello(@Param("NC") String nc);
}
