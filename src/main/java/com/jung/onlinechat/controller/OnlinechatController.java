package com.jung.onlinechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.jung.onlinechat.entity.UserChat;
import com.jung.onlinechat.service.OnlinechatServer;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        String resMsg = onlinechatServer.insertmag(fromuser,touser,msg);
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("resMsg",resMsg);
        return jsonObject.toJSONString();
    }

    @RequestMapping("/recall")
    @ResponseBody
    private String RecallMassgae(String msg){
        Boolean bool = onlinechatServer.recall(msg);
        JSONObject jsonObject =  new JSONObject();
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
        //查询我发送的信息
        List<UserChat> list = onlinechatServer.selectMsg(fromuser,touser);
        //查询对方的内容
        List<UserChat> otherlist =  onlinechatServer.selectMsg(touser,fromuser);

//        //TODO 通过时间进行排序，此处未实现，仅用于测试
//        for(int i=0;i<otherlist.size();i++){
//            list.add(otherlist.get(i));
//        }
//        //TODO END
        List<UserChat> resList = onlinechatServer.combinMsg(list,otherlist);
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("result",resList);
        return jsonObject.toJSONString();
    }
    @RequestMapping("/upload")
    private String UpdateFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        System.out.println("现在正在进行图片上传");
        String fileName = System.currentTimeMillis()+multipartFile.getOriginalFilename();
        if(!multipartFile.isEmpty()){
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),new File("E:\\A",fileName));
        }
        return "redirect:/home";
//        String url="";
//        FileUtils.copyFile(new File("E:\\A","A.jpg"),new File("E:\\A","result.jpg"));
    }
}
