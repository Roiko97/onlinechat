package com.jung.onlinechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.jung.onlinechat.entity.UserChat;
import com.jung.onlinechat.service.OnlinechatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 负责执行交流业务的控制器
 */
@Controller
@RequestMapping("/onlinechat")
public class OnlinechatController {

    @Autowired
    private OnlinechatServer onlinechatServer;

    @RequestMapping("/save")
    @ResponseBody
    private String SaveMassage(String fromuser, String touser,String msg){
        Boolean bool = onlinechatServer.insertmag(fromuser,touser,msg);
        JSONObject jsonObject =  null;
        if(bool == true){
            jsonObject.put("result",true);
        }else{
            jsonObject.put("result",false);
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/recall")
    @ResponseBody
    private String RecallMassgae(String msg){
        Boolean bool = onlinechatServer.recall(msg);
        JSONObject jsonObject =  null;
        if(bool == true){
            jsonObject.put("result",true);
        }else{
            jsonObject.put("result",false);
        }
        return jsonObject.toJSONString();
    }

    @RequestMapping("/select")
    @ResponseBody
    private String SelectMsg(String fromuser,String touser){
        List<UserChat> list = onlinechatServer.selectMsg(fromuser,touser);
        JSONObject jsonObject =  null;
        jsonObject.put("result",list);
        return jsonObject.toJSONString();
    }
}
