package com.tor.classify.controller;

import com.jcraft.jsch.*;
import com.tor.classify.result.CodeMsg;
import com.tor.classify.result.Result;
import com.tor.classify.service.GrabPacketsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Slf4j
@Controller
public class GrabPacketsController {

    @Autowired
    private GrabPacketsService grabPacketsService;

    @RequestMapping(value = "/index")
    public String index(ModelMap modelMap) throws InterruptedException {
        return "index";
    }

    @RequestMapping(value = "/grab_packets")
    public String grabPacket(ModelMap modelMap, @RequestParam(value = "grabPlace", required = false, defaultValue = "server") String grabPlace,
                             @RequestParam(value = "command", required = false, defaultValue = "ifconfig") String command,
                             @RequestParam(value = "packetCount", required = false, defaultValue = "5") int packetCount,
                             @RequestParam(value = "protocol", required = false, defaultValue = "ip")String protocol,
                             @RequestParam(value = "selectWay", required = false, defaultValue = "0")int selectWay,
                             HttpServletResponse response) throws InterruptedException, JSchException, SftpException, IOException {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd+HH-mm-ss");
        String time=formatter.format(date);
        String fileName = "ServerPcap_" + time + ".pcap";
//        String  grabPlace = "server";
//        String cmd;
//        int packetCount;
//        String protocol;
//        int selectWay = 0;
        log.info("grab_packets:参数为：grabPlace：{}，command：{}，packetCount:{},protocol:{},selectWay:{}",grabPlace, command, packetCount, protocol, selectWay);
        grabPacketsService.grabPackets(fileName, grabPlace, command, packetCount, protocol, selectWay);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write("<script>alert('正在抓包，抓包成功之后将存入数据库，数据包名称为："+ fileName + "！网页将跳转到抓包页面');  window.location='index';</script>");
        response.getWriter().flush();
        return "index";
    }





    public static void main(String[] args) throws IOException, JSchException, SftpException {
//        connect111();
    }

}

