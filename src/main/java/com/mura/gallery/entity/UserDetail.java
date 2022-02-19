package com.mura.gallery.entity;

import lombok.Data;

/**
 * @author Akutagawa Murasame
 * 用户的详细信息，用户可以自行设置
 */
@Data
public class UserDetail {
    private Integer userId;
    private String trueName;
    private String location;
    private String avatarPath;
    private String email;
}
