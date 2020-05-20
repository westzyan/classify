package com.tor.classify.service;

import com.tor.classify.dao.PacketDao;
import com.tor.classify.domain.Packet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacketService {
    @Autowired
    private PacketDao packetDao;

    public int insertPacket(Packet packet){
        return packetDao.insertPacket(packet);
    }
}
