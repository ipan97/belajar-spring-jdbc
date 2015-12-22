/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ipan97.example.my.project.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class Users {
    private Integer id;
    private String username;
    private String password;
    private List<UsersDetail> detailUser=new ArrayList<>();
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UsersDetail> getDetailUser() {
        return detailUser;
    }

    public void setDetailUser(List<UsersDetail> detailUser) {
        this.detailUser = detailUser;
    }
    
}
