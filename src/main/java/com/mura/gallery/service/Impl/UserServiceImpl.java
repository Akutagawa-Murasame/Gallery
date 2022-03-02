package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.RegisterStatus;
import com.mura.gallery.entity.User;
import com.mura.gallery.entity.UserDetail;
import com.mura.gallery.exception.RegistrationFailedException;
import com.mura.gallery.mapper.UserMapper;
import com.mura.gallery.service.RoleService;
import com.mura.gallery.service.UserDetailService;
import com.mura.gallery.service.UserService;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Akutagawa Murasame
 * 对于User的具体操作
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource(type = UserMapper.class)
    UserMapper userMapper;

    @Resource(type = RoleService.class)
    RoleService roleService;

    @Resource(type = UserDetailService.class)
    UserDetailService userDetailService;

    /**
     * 注册用户
     * @param user 网页提交的用户信息
     * @return 注册状态
     * REPEATED_USERNAME：用户名重复
     * REGISTRY_SUCCESS：注册成功
     * REGISTRY_FAILED：注册失败
     */
    @Override
    @Transactional(rollbackFor = RegistrationFailedException.class)
    public RegisterStatus reg(User user, UserDetail userDetail) {
        User username = userMapper.selectByUsername(user.getUsername());

//        在数据库中查询到了则不为null，说明重复
        if (username != null) {
            return RegisterStatus.REPEATED_USERNAME;
        }

//        数据库中的密码是加密过的，这里使用的MD5Encoder非对称散列
//        MD5Encoder加密的字节流需要是16字节的
        byte[] temp = Arrays.copyOf(user.getPassword().getBytes(StandardCharsets.UTF_8), 16);
        user.setPassword(MD5Encoder.encode(temp));

//        插入用户
        int userInsertResult = userMapper.insertOne(user);

//        设置userDetail的id
        userDetail.setUserId(user.getId());

//        默认都是会员，也就是member，另外还有超级会员super和黑名单blacklist
        int roleInsertResult = roleService.insertOne(user.getId(), "member");

//        插入用户头像（路径）
        int userDetailInsertResult = userDetailService.insertAtLeastOneColumn(userDetail);

//        没成功则回滚，手动调用回滚，便于返回RegisterStatus
        try {
            if (!(userInsertResult == 1 && roleInsertResult == 1 && userDetailInsertResult == 1)) {
                throw new RegistrationFailedException("database access error");
            }
        } catch (RegistrationFailedException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return RegisterStatus.REGISTRY_FAILED;
        }

        return  RegisterStatus.REGISTRY_SUCCESS;
    }
}
