package com.mura.gallery.entity;

import lombok.Data;

/**
 * @author Akutagawa Murasame
 * 用户信息
//
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Role roles;

    /**
     * 传过来的参数一般只有用户名和密码，所以定义这个构造函数
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}
}
