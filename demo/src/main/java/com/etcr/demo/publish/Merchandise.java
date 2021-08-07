package com.etcr.demo.publish;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Merchandise implements Serializable {
    @Id
    @GeneratedValue(generator = "merchandise_generator")
    @GenericGenerator(name = "merchandise_generator", strategy = "uuid")
    private  String goodid;
    private String title;
    private String post_userid;
    private String kind;
    private String time;
    private String price;
    private String contact;
    private String info;
    private String picturechar;


    public void setGoodid(String goodid) {this.goodid = goodid;}

    public String getGoodid() {return  goodid;}

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    public void setPost_userid(String post_userid) {
        this.post_userid = post_userid;
    }

    public String getPost_userid() {
        return post_userid;
    }

    public void setContact(String contact) {this.contact=contact;}

    public String getContact() {return contact;}

    public void setPicturechar(String picturechar) {this.picturechar=picturechar;}

    public String getPicturechar() {return picturechar;}
}
