package com.keyou.fdcda.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Wataru on 2017-07-08.
 */
public class User implements Serializable {
    static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String password;
    private Long credit;
    private Date createdAt;
    private Date updatedAt;
    
    public User() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCredit() {
        return credit;
    }

    public void setCredit(Long credit) {
        this.credit = credit;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
