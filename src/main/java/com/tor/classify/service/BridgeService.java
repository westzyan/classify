package com.tor.classify.service;

import com.tor.classify.dao.BridgeDao;
import com.tor.classify.domain.Bridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class BridgeService {

    @Autowired
    private BridgeDao bridgeDao;

    public Integer insertBridges(List<Bridge> list){
        return bridgeDao.insertBridges(list);
    }

    public List<Bridge> selectBridges(){
        return bridgeDao.selectBridges();
    }

}
