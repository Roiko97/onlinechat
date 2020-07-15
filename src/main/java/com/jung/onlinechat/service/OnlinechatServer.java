package com.jung.onlinechat.service;

import com.jung.onlinechat.entity.UserChat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface OnlinechatServer {
    public Boolean recall(String msg);
    public String insertmag(String fromuser, String touser,String msg,String type);
    public List<UserChat> selectMsg(String fromuser, String touser);

    //以下两个函数是对聊天记录进行时间排序
    public List<UserChat> combinMsg(List<UserChat>list1 , List<UserChat>list2);
    public Boolean dateComparison(String s1, String s2);


    //文件下载
    public ResponseEntity<Object> FileDownLoad(String FileName) throws UnsupportedEncodingException, FileNotFoundException;
}
