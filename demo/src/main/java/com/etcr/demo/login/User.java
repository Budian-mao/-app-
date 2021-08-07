package com.etcr.demo.login;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class User implements Serializable {
    @Id
    private String id;
    private String password;
    private String name;
    private String depart;
    private String phone;
    private String qq;

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() { return name; }

    public String getDepart() { return depart; }

    public String getPhone() {
        return phone;
    }

    public String getQq() {
        return qq;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) { this.name = name; }

    public void setDepart(String depart) { this.depart = depart;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
