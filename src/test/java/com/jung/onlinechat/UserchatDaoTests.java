package com.jung.onlinechat;

import com.jung.onlinechat.dao.UserchatDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/**
 * 项目中集成socket时，进行单元测试会报错，解决方案
 * https://blog.csdn.net/chunjusu2447/article/details/100820520
 */
public class UserchatDaoTests {

    @Autowired
    UserchatDao userchatDao;

    @Test
    public void insertMag(){
        String msg = "哇 你居然是张三，我是罗翔";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());
        userchatDao.insertmag("李四","张三","<"+date+"> "+msg);
    }

    @Test
    public void selectMsg(){
        //userchatDao.selectMsg()
    }
}
