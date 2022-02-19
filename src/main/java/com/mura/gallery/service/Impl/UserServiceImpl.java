package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.RegisterStatus;
import com.mura.gallery.entity.User;
import com.mura.gallery.exception.RegistrationFailedException;
import com.mura.gallery.mapper.RoleMapper;
import com.mura.gallery.mapper.UserMapper;
import com.mura.gallery.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Akutagawa Murasame
 * 对于User的具体操作
 */
@Service
@Transactional(rollbackFor = RegistrationFailedException.class)
public class UserServiceImpl implements UserService {
    @Resource(type = UserMapper.class)
    UserMapper userMapper;

    @Resource(type = PasswordEncoder.class)
    PasswordEncoder passwordEncoder;

    @Resource(type = RoleMapper.class)
    RoleMapper roleMapper;

    /**
     * 注册用户
     * @param user 网页提交的用户信息
     * @return 注册状态
     * REPEATED_USERNAME：用户名重复
     * REGISTRY_SUCCESS：注册成功
     * REGISTRY_FAILED：注册失败
     */
    @Override
    public RegisterStatus reg(User user) {
        User username = userMapper.selectByUsername(user.getUsername());

//        在数据库中查询到了则不为null，说明重复
        if (username != null) {
            return RegisterStatus.REPEATED_USERNAME;
        }

        //数据库中的密码是加密过的，这里使用的BCryptPasswordEncoder非对称加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

//        默认用户可用
        user.setEnabled(true);

        int userInsertResult = userMapper.insertOne(user);

        //默认都是会员，也就是member，另外还有超级会员super和黑名单blacklist
        int roleInsertResult = roleMapper.insertOne(user.getId(), "member");

//        没成功则回滚
        if (!(userInsertResult == 1 && roleInsertResult == 1)) {
            throw new RegistrationFailedException(RegisterStatus.REGISTRY_FAILED.toString());
        }

        return  RegisterStatus.REGISTRY_SUCCESS;
    }
}
