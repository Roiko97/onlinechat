package com.jung.onlinechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@ServletComponentScan
public class OnlinechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinechatApplication.class, args);
    }

}
