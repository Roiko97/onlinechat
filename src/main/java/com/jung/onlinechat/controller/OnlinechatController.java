package com.jung.onlinechat.controller;


import com.alibaba.fastjson.JSONObject;
import com.jung.onlinechat.entity.UserChat;
import com.jung.onlinechat.service.OnlinechatServer;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    private String SaveMassage(HttpServletRequest httpServletRequest,String fromuser, String touser,String msg,String type){
        //注：此处的resMsg包含了时间戳信息
        String resMsg = onlinechatServer.insertmag(fromuser,touser,msg,type);
        HttpSession session  = httpServletRequest.getSession();
        if(type.equals("file")){
            session.setAttribute("fileNewName",resMsg);
        }
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("resMsg",resMsg);
        jsonObject.put("type",type);
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

        List<UserChat> resList = onlinechatServer.combinMsg(list,otherlist);
        JSONObject jsonObject =  new JSONObject();
        jsonObject.put("result",resList);
        return jsonObject.toJSONString();
    }
    @RequestMapping("/upload")
    private String UpdateFile(@RequestParam("file") MultipartFile multipartFile,HttpServletRequest httpServletRequest) throws IOException {
        HttpSession session = httpServletRequest.getSession();
        System.out.println("现在正在进行图片上传");
        //String fileName = System.currentTimeMillis()+multipartFile.getOriginalFilename();
        String fileName = session.getAttribute("fileNewName").toString();
        System.out.println(multipartFile.getBytes());
        if(!multipartFile.isEmpty()){
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),new File("E:\\A",fileName));
        }
        return "redirect:/home";
    }
    @RequestMapping(value = "/download")
    private ResponseEntity<Object> DownLoadFile(HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException {
        System.out.println("aa");
        String FileName = request.getParameter("id");
        return onlinechatServer.FileDownLoad(FileName);
    }
}
