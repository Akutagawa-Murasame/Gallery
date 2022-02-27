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

    /**
     * 至少插入一列，一个字段（至少插入用户头像）
     */
    int insertAtLeastOneColumn(UserDetail userDetail);
}
