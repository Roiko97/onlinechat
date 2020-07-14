package com.jung.onlinechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 负责进行跳转的控制器
 */
@Controller
@RequestMapping
public class Jump {

    /**
     * 跳转到非富文本页面
     * @return
     */
    @RequestMapping("/home")
    public String JumpHome(){
        return "onlinechat";
    }
    @RequestMapping("/login")
    public String jumpLogin(){
        return "login";
    }
    /**
     * 跳转到富文本框的页面
     */
//    @RequestMapping("/home")
//    public String JumpCs(){return "onlinechatByWangEditor";}
}
