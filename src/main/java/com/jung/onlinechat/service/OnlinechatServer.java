package com.jung.onlinechat.service;

import com.jung.onlinechat.entity.UserChat;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OnlinechatServer {
    public Boolean recall(String msg);
    public String insertmag(String fromuser, String touser,String msg);
    public List<UserChat> selectMsg(String fromuser, String touser);
    public List<UserChat> combinMsg(List<UserChat>list1 , List<UserChat>list2);
    public Boolean dateComparison(String s1, String s2);
}
