package com.skypan.easytochewroot;

public class Message {
    private  String mes_id;
    private  String time;
    private String send_id;
    private String receive_id;
    private String text;

    public String getMes_id() {
        return mes_id;
    }

    public void setMes_id(String mes_id) {
        this.mes_id = mes_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSend_id() {
        return send_id;
    }

    public void setSend_id(String send_id) {
        this.send_id = send_id;
    }

    public String getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(String receive_id) {
        this.receive_id = receive_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text= text;
    }

}