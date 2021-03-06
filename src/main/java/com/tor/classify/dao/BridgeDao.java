package com.tor.classify.dao;

import com.tor.classify.domain.Bridge;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

@Mapper
@Repository
public interface BridgeDao {

    @Insert({"<script> " +
            "insert into bridge(ip, country, country_code, city, longitude, latitude, create_time) values"+
            "<foreach collection=\"list\" item=\"item\" index=\"index\"  separator=\",\"> "+
            "(#{item.ip},#{item.country},#{item.countryCode},#{item.city},#{item.longitude},#{item.latitude},#{item.createTime})"+
            "</foreach> " +
            "</script>"})
    Integer insertBridges(List<Bridge> list);

    @Select("select * from bridge order by id desc")
    @Results({
            @Result(property = "countryCode", column = "country_code"),
            @Result(property = "createTime", column = "create_time")
    })
    List<Bridge> selectBridges();

}
