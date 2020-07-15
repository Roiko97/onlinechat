package com.jung.onlinechat;

import com.jung.onlinechat.dao.UserchatDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
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
        userchatDao.insertmag("李四","张三","<"+date+"> "+msg,"text");
    }

    @Test
    public void TestAboutString(){
        //userchatDao.selectMsg()
        //<2020-07-11 21:43:01> 李四你好，我是张三
        String date = "<2020-07-11 21:43:01>";
        String res = date.substring(1,date.indexOf(">"));
        System.out.println(res);
    }
    @Test
    public void Time(){
        SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//创建日期转换对象：年月日 时分秒
        String date = "2018-11-11 11:11:11"; //假设 设定日期是 2018-11-11 11:11:11
        Date today = new Date(); 	     //今天 实际日期是  Debug：Wed Nov 12 12:00:18 CST 2018
        try {
            Date dateD = sdf.parse(date);    //转换为 date 类型 Debug：Sun Nov 11 11:11:11 CST 2018
            boolean flag = dateD.getTime() >= today.getTime();
            System.err.println("flag = "+flag);  // flag = false
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}
