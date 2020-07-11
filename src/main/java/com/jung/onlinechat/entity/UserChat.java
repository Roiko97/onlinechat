package com.jung.onlinechat.entity;

public class UserChat {
    private String Message;
    private String FromUser;
    private String ToUser;

    public UserChat(String message, String fromUser, String toUser) {
        Message = message;
        FromUser = fromUser;
        ToUser = toUser;
    }
    public UserChat(){

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getFromUser() {
        return FromUser;
    }

    public void setFromUser(String fromUser) {
        FromUser = fromUser;
    }

    public String getToUser() {
        return ToUser;
    }

    public void setToUser(String toUser) {
        ToUser = toUser;
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "Message='" + Message + '\'' +
                ", FromUser='" + FromUser + '\'' +
                ", ToUser='" + ToUser + '\'' +
                '}';
    }
}
