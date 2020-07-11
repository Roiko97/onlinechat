package com.jung.onlinechat.service;

import com.jung.onlinechat.entity.UserChat;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OnlinechatServer {
    public Boolean recall(String msg);
    public Boolean insertmag(String fromuser, String touser,String msg);
    public List<UserChat> selectMsg(String fromuser, String touser);
}
