package com.skypan.easytochewroot;

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;
    private String content;//消息内容
    private int type;//消息类型，分为发送（TYPE_SEND）和接收的（TYPE_RECEIVED）

    public Msg(String content, int type) {
        // TODO Auto-generated constructor stub
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}