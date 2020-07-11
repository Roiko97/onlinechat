package com.jung.onlinechat.service.impl;

import com.jung.onlinechat.dao.UserchatDao;
import com.jung.onlinechat.entity.UserChat;
import com.jung.onlinechat.service.OnlinechatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 插入信息
     * @param fromuser
     * @param touser
     * @param msg
     * @return
     */
    @Override
    public Boolean insertmag(String fromuser, String touser, String msg) {
        Integer result = userchatDao.insertmag(fromuser,touser,msg);
        if(result !=0){
            return true;
        }else{
            System.out.println("ERROR  --- local:OnlinechatServerImpl func:insertmag ---");
            return false;
        }
    }

    /**
     * 查询聊天信息
     * @param fromuser
     * @param touser
     * @return
     */
    @Override
    public List<UserChat> selectMsg(String fromuser, String touser) {
        return userchatDao.selectMsg(fromuser,touser);
    }
}
