package com.jung.onlinechat.entity;

public class UserChat {
    private String Message;
    private String FromUser;
    private String ToUser;
    /**
     * Type类型
     * 1.text
     * 2.file
     */
    private String Type;

    public UserChat(String message, String fromUser, String toUser,String type) {
        Message = message;
        FromUser = fromUser;
        ToUser = toUser;
        Type = type;
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @Override
    public String toString() {
        return "UserChat{" +
                "Message='" + Message + '\'' +
                ", FromUser='" + FromUser + '\'' +
                ", ToUser='" + ToUser + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
