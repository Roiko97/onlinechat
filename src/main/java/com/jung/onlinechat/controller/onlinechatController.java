package com.jung.onlinechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.jung.onlinechat.entity.UserChat;
import com.jung.onlinechat.service.OnlinechatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/onlinechat")
public class onlinechatController {

    @Autowired
    private JSONObject jsonObject;

    @Autowired
    private OnlinechatServer onlinechatServer;

    @RequestMapping("/save")
    @ResponseBody
    private String SaveMassage(String fromuser, String touser,String msg){
        Boolean bool = onlinechatServer.insertmag(fromuser,touser,msg);
        if(bool == true){
            return jsonObject.put("result",true).toString();
        }else{
            return jsonObject.put("result",false).toString();
        }
    }

    @RequestMapping("/recall")
    @ResponseBody
    private String RecallMassgae(String msg){
        Boolean bool = onlinechatServer.recall(msg);
        if(bool == true){
            return jsonObject.put("result",true).toString();
        }else{
            return jsonObject.put("result",false).toString();
        }
    }

    @RequestMapping("/select")
    @ResponseBody
    private String SelectMsg(String fromuser,String touser){
        List<UserChat> list = onlinechatServer.selectMsg(fromuser,touser);
        return jsonObject.put("result",list).toString();
    }
}
