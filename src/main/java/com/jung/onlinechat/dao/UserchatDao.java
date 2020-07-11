package com.jung.onlinechat.dao;

import com.jung.onlinechat.entity.UserChat;

import java.util.List;

public interface UserchatDao {
    /**
     * 撤回用户消息
     * @param msg
     * @return
     */
    public Integer recall(String msg);

    /**
     * 保存用户信息
     * @param fromuser
     * @param touser
     * @param msg
     * @return
     */
    public Integer insertmag(String fromuser, String touser,String msg);

    /**
     * 查询用户信息
     * @param fromuser
     * @param touser
     * @return
     */
    public List<UserChat> selectMsg(String fromuser, String touser);
}
