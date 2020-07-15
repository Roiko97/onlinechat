package com.jung.onlinechat.service.impl;

import com.jung.onlinechat.dao.UserchatDao;
import com.jung.onlinechat.entity.UserChat;
import com.jung.onlinechat.service.OnlinechatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OnlinechatServerImpl implements OnlinechatServer {
    @Autowired
    private UserchatDao userchatDao;

    /**
     * 撤回指定信息
     * 信息存储格式<日期>信息
     * @param msg
     * @return
     */
    @Override
    public Boolean recall(String msg) {
        //plus.限制时间进行撤回
        Integer result = userchatDao.recall(msg);
        if(result ==1){
            return true;
        }else if(result == 0){
            return false;
        }else{
            System.out.println("ERROR  --- local:OnlinechatServerImpl func:recall ---");
            return false;
        }
    }

    /**
     * 插入信息 （注：该函数将发送信息增加了时间戳）
     * 时间戳格式{Time} 信息  注意中间是存在空格的
     * @param fromuser 发送者
     * @param touser 接收者
     * @param msg 信息
     * @param type 信息类型
     * @return 增加了时间戳的信息
     */
    @Override
    public String insertmag(String fromuser, String touser, String msg,String type) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");//设置日期格式
        String date = df.format(new Date());
        msg = "{"+date+"} "+msg;
        Integer result = userchatDao.insertmag(fromuser,touser,msg,type);
        return msg;
    }

    /**
     * 查询用户之间所有的聊天信息
     * @param fromuser
     * @param touser
     * @return
     */
    @Override
    public List<UserChat> selectMsg(String fromuser, String touser) {
        return userchatDao.selectMsg(fromuser,touser);
    }


    /**
     * 用户聊天信息按照时间顺序进行合并（主函数）
     * @param list1
     * @param list2
     * @return
     */
    @Override
    public List<UserChat> combinMsg(List<UserChat> list1, List<UserChat> list2) {
        List<UserChat> result = new ArrayList<>();
        int flag1=0;int flag2=0;
        if(list1.size()!=0 && list2.size()!=0){
            while(flag1 !=list1.size() && flag2!=list2.size()){
                String data1 = list1.get(flag1).getMessage().substring(1,list1.get(flag1).getMessage().indexOf("}"));
                String data2 = list2.get(flag2).getMessage().substring(1,list2.get(flag2).getMessage().indexOf("}"));
                boolean res = dateComparison(data1,data2);
                if(res == true) { //data1 >= data2
                    result.add(list2.get(flag2));
                    flag2++;
                }else{
                    result.add(list1.get(flag1));
                    flag1++;
                }
            }
            while(flag1 != list1.size()){
                result.add(list1.get(flag1));
                flag1++;
            }
            while(flag2 != list2.size()){
                result.add(list2.get(flag2));
                flag2++;
            }
        }else if(list1.size() ==0 && list2.size()!=0){
            while(flag2 != list2.size()){
                result.add(list2.get(flag2));
                flag2++;
            }
        }else if(list2.size() ==0 && list1.size()!=0){
            while(flag1 != list1.size()){
                result.add(list1.get(flag1));
                flag1++;
            }
        }else{ //both list1 and list2 are equals zero
            result = null;
        }
        return result;
    }

    /**
     * 用户聊天信息按照时间顺序进行合并（分函数）
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public Boolean dateComparison(String s1, String s2) {
        SimpleDateFormat sdf  =new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");//创建日期转换对象：年月日 时分秒
        Boolean res = true;
        try {
            Date date1 = sdf.parse(s1);
            Date date2 = sdf.parse(s2);
            res = date1.getTime() >= date2.getTime()?true:false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public ResponseEntity<Object> FileDownLoad(String FileName) throws UnsupportedEncodingException, FileNotFoundException {
        File file = new File("E:\\A\\"+FileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream((file)));
        //设置文件名编码，否则下载后无法显示中文
        String finalFileName = URLEncoder.encode(file.getName(),"UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",finalFileName));
        //headers.add("Content-Disposition",String.format("attachment;abc;filename=",file.getName()));
        headers.add("Cache-Control","no-cache,no-store,must-revalidate");
        headers.add("Pragma","no-cache");
        headers.add("Content-type","charset=utf-8");
        headers.add("Expires","0");
        ResponseEntity<Object> responseEntity = ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/text;charset=utf-8"))
                .body(resource);
        return responseEntity;
    }

}
