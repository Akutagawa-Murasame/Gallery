package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.UserDetail;
import com.mura.gallery.mapper.UserDetailMapper;
import com.mura.gallery.service.UserDetailService;

import javax.annotation.Resource;

/**
 * @author Akutagawa Murasame
 */
public class UserDetailServiceImpl implements UserDetailService {
    @Resource(type = UserDetailMapper.class)
    UserDetailMapper userDetailMapper;

    @Override
    public UserDetail selectById(Integer userId) {
        return userDetailMapper.selectById(userId);
    }
}
