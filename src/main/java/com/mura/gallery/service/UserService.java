package com.mura.gallery.service;

import com.mura.gallery.entity.RegisterStatus;
import com.mura.gallery.entity.User;

/**
 * @author Akutagawa Murasame
 */
public interface UserService {
    /**
     * 注册用户
     * @param user 网页提交的用户信息
     * @return 注册状态
     */
    RegisterStatus reg(User user);
}
