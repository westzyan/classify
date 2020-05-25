package com.tor.classify.controller;

import com.tor.classify.domain.Bridge;
import com.tor.classify.result.Result;
import com.tor.classify.service.BridgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
public class BridgeController {
    @Autowired
    private BridgeService bridgeService;

    @RequestMapping("/bridge")
    public String bridge(ModelMap modelMap){
        List<Bridge> bridgeList = bridgeService.selectBridges();
        modelMap.addAttribute("result", Result.success(bridgeList));
        modelMap.addAttribute("total", bridgeList.size());
        return "bridge";
    }

    @RequestMapping("/bridge_charts")
    @ResponseBody
    public String bridgeCharts(ModelMap modelMap){
        List<Bridge> bridgeList = bridgeService.selectBridges();

//        StringBuilder sb = new StringBuilder();
//        sb.
//        for (Bridge bridge : bridgeList) {
//            sb.append()
//        }



        modelMap.addAttribute("result", Result.success(bridgeList));
        return "bridge";
    }

}
