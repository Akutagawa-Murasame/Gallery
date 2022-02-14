package com.mura.gallery.service;

import com.mura.gallery.entity.UserDetail;

/**
 * @author Akutagawa Murasame
 */
public interface UserDetailService {
    UserDetail selectById(Integer userId);
}
