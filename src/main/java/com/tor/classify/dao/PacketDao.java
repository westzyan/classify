package com.tor.classify.dao;


import com.tor.classify.domain.Packet;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PacketDao {

    @Insert("insert into packet (packetName, packetPath, type, csvPath) values (#{packetName},#{packetPath},#{type},#{csvPath})")
    int insertPacket(Packet packet);
}
