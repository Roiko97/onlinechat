package com.jung.onlinechat.dao;

import com.jung.onlinechat.entity.User;

public interface UserLoginDao {
    public User userSelect(String username);
}
