package com.mura.gallery.entity;

import lombok.Data;

/**
 * @author Akutagawa Murasame
 */
@Data
public class UserDetail {
    private Integer userId;
    private String trueName;
    private String location;
    private String avatarPath;
    private String email;
}
