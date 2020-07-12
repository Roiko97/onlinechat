package com.jung.onlinechat.service.impl;

import com.jung.onlinechat.dao.UserLoginDao;
import com.jung.onlinechat.entity.User;
import com.jung.onlinechat.service.UserLoginServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserLoginServerImpl implements UserLoginServer {
    @Autowired
    UserLoginDao userLoginDao;
    @Autowired
    HttpSession session;
    public Boolean Login(String username){
        User user = userLoginDao.userSelect(username);
        if(user == null)
            return false;
        session.setAttribute("username",user.getName());
        System.out.println("目前登陆的用户是： "+ user.getName());
        return true;
    }
}
