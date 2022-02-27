package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.UserDetail;
import com.mura.gallery.mapper.UserDetailMapper;
import com.mura.gallery.service.UserDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

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
//        我们不做这一步判断，也能得到正确的结果
//        但是这样的话会进行一次无效的数据库查询
//        在访问量大的时候，会带来一些压力
        if (userId == null) {
            return null;
        }

        return userDetailMapper.selectById(userId);
    }

    /**
     * 至少插入一列，一个字段（至少插入用户头像）
     * @param userDetail 用户详细信息对象
     * @return 返回1则成功，返回0则失败
     */
    @Override
    public int insertAtLeastOneColumn(UserDetail userDetail) {
        if ((userDetail.getAvatarPath() == null) || (Objects.equals(userDetail.getAvatarPath(), ""))) {
            return 0;
        }

        return userDetailMapper.insertAtLeastOneColumn(userDetail);
    }
}
