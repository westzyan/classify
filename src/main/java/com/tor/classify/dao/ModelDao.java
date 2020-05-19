package com.tor.classify.dao;

import com.tor.classify.domain.Model;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ModelDao {
    @Select("select * from model order by id desc limit 0,1")
    Model getLastModel();
}
