package com.jung.onlinechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Jump {

    @RequestMapping("/home")
    public String JumpHome(){
        return "wechat";
    }
}
