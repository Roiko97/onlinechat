package com.jung.onlinechat.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
/**
 * ImportResource引入资源文件有三种方式：
 *     1.直接引入，该路径就是src/resources/下面的文件：file
 *     2.classpath引入：该路径就是src/java下面的配置文件：classpath:file
 *     3.引入本地文件：该路径是一种绝对路径：file:D://....
 */
/**
 * 该类用于解析自己配置的bean文件
 */
@ImportResource(locations = {"classpath:mybean.xml"})
public class MyConfig {
//    @Bean
//    public Integer setRfc(){
//        // 指定jre系统属性，允许特殊符号， 如{} 做入参，其他符号按需添加。见 tomcat的HttpParser源码。
//        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{} ：");
//        return 0;
//    }
}
