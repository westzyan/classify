package com.tor.classify.task;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tor.classify.domain.Bridge;
import com.tor.classify.domain.IPEntity;
import com.tor.classify.service.BridgeService;
import com.tor.classify.util.DownLoadUtil;
import com.tor.classify.util.IPUtil;
import com.tor.classify.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.*;
import java.util.*;


@Slf4j
public class ScheduledTask {

    @Autowired
    private BridgeService bridgeService;

    /**
     * 从服务器上下载网桥的IP地址文件，存到本地文件夹，然后读取文件，解析IP得到地理位置信息，存到Bridge数据库
     */
    //TODO 需要定时任务
//    @Async("taskExecutor")
//    @Scheduled(cron = "? 0,20,40 * * * ? ")
    public void bridgeInsert() throws JSchException, SftpException, IOException {
        DownLoadUtil.downloadFromRemote(PropertiesUtil.getRemoteFilePath(), "bridge.txt", PropertiesUtil.getLocalFilePath());

        List<Bridge> bridgeList = new ArrayList<>();

        //将数据库中IP地址读取出来放到hashset
        Set<String> IPSet = new HashSet<>();
        List<Bridge> IPList = bridgeService.selectBridges();
        for (Bridge bridge : IPList) {
            IPSet.add(bridge.getIp());
        }
        //读取bridge.txt
        File file = new File(PropertiesUtil.getLocalFilePath() + "bridge.txt");

        if(file.isFile() && file.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),"UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while((lineTxt = bufferedReader.readLine()) != null){
                if (!IPSet.contains(lineTxt)){
                    Bridge bridge = new Bridge();
                    IPEntity ipEntity = IPUtil.getIPMsg(lineTxt);
                    bridge.setCity(ipEntity.getCityName());
                    bridge.setCountry(ipEntity.getCountryName());
                    bridge.setCountryCode(ipEntity.getCountryCode());
                    bridge.setIp(lineTxt);
                    bridge.setLatitude(ipEntity.getLatitude());
                    bridge.setLongitude(ipEntity.getLongitude());
                    bridge.setCreateTime(new Date());
                    bridgeList.add(bridge);
                }
            }
            bridgeService.insertBridges(bridgeList);
        }else {
            log.error("找不到bridge.txt");
        }

    }
}
