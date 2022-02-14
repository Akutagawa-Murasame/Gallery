package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.RegisterStatus;
import com.mura.gallery.entity.Role;
import com.mura.gallery.entity.User;
import com.mura.gallery.mapper.RoleMapper;
import com.mura.gallery.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Akutagawa Murasame
 */
@Service("userDetailsService")
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource(type = UserMapper.class)
    UserMapper userMapper;

    @Resource(type = RoleMapper.class)
    RoleMapper roleMapper;

    @Resource(type = PasswordEncoder.class)
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(s);

//        security内部会catch这个异常
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<Role> roles = roleMapper.selectByUserId(user.getId());
        user.setRoles(roles);

        return user;
    }

    public RegisterStatus reg(User user) {
        User loadUserByUsername = userMapper.selectByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return RegisterStatus.REPEATED_USERNAME;
        }

        //数据库中的密码是加密过的
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        int userInsertResult = userMapper.insertOne(user);

        //默认都是会员，也就是member，另外还有超级会员super和黑名单blacklist
        int roleInsertResult = roleMapper.insertOne(user.getId(), "member");

        return (userInsertResult == 1 && roleInsertResult == 1) ?
                RegisterStatus.REGISTRY_SUCCESS : RegisterStatus.REGISTRY_FAILED;
    }
}
