package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.UserDetail;
import com.mura.gallery.mapper.UserDetailMapper;
import com.mura.gallery.service.UserDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Akutagawa Murasame
 * 对于UserDetail的具体操作
 */
@Service
public class UserDetailServiceImpl implements UserDetailService {
    @Resource(type = UserDetailMapper.class)
    UserDetailMapper userDetailMapper;

    /**
     * 根据userId查询UserDetail
     * @param userId userId
     * @return UserDetail对象
     */
    @Override
    public UserDetail selectById(Integer userId) {
        return userDetailMapper.selectById(userId);
    }
}
