package com.mura.gallery.service;

import com.mura.gallery.entity.UserDetail;

/**
 * @author Akutagawa Murasame
 * 对于UserDetail的操作
 */
public interface UserDetailService {
    /**
     * 根据userId查询UserDetail
     * @param userId userId
     * @return UserDetail对象
     */
    UserDetail selectById(Integer userId);
}
