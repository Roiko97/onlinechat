package com.jung.onlinechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 负责进行跳转的控制器
 */
@Controller
@RequestMapping
public class Jump {

    @RequestMapping("/home")
    public String JumpHome(){
        return "onlinechat";
    }
    @RequestMapping("/login")
    public String jumpLogin(){
        return "login";
    }
}
