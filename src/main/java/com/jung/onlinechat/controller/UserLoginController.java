package com.jung.onlinechat.controller;

import com.jung.onlinechat.service.UserLoginServer;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 负责处理登入业务的控制
 */
@Controller
public class UserLoginController {
    @Autowired
    UserLoginServer userLoginServer;

    @RequestMapping("/uselogin")
    @ResponseBody
    public Boolean Login(@Param("name")String name){
        System.out.println("this");
        Boolean res = userLoginServer.Login(name);
        return res;
    }
}
