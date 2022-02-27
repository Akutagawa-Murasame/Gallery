package com.mura.gallery.service.Impl;

import com.mura.gallery.entity.Role;
import com.mura.gallery.entity.User;
import com.mura.gallery.mapper.RoleMapper;
import com.mura.gallery.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Akutagawa Murasame
 * 实现了Spring security的UserDetailsService接口的loadUserByUsername
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource(type = UserMapper.class)
    UserMapper userMapper;

    @Resource(type = RoleMapper.class)
    RoleMapper roleMapper;

    /**
     * 比对用户名和密码是否正确
     * @param s 网页输入的用户名
     * @return 如果用户名和密码正确，则
     * @throws UsernameNotFoundException 用户名可能没有注册，这个时候spring security会自动将这个信息在网页上显示出来
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.selectByUsername(s);

//        security内部会catch这个异常，并将相关信息显示在网页上
        if (null == user) {
            throw new UsernameNotFoundException("用户名不存在");
        }

//        查询用户的角色，不同的角色访问的内容不同
        List<Role> roles = roleMapper.selectByUserId(user.getId());
        user.setRoles(roles);

        return user;
    }
}
