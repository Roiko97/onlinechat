package com.jung.onlinechat.controller;

import com.jung.onlinechat.service.UserLoginServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 负责处理登入业务的控制
 */
@Controller
public class UserLoginController {
    @Autowired
    UserLoginServer userLoginServer;

    @RequestMapping("/uselogin")
    public String Login(String name){
        Boolean res = userLoginServer.Login(name);
        return "redirect:home";
    }
}
