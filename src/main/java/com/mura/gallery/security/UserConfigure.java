package com.mura.gallery.security;

import com.mura.gallery.entity.Role;
import com.mura.gallery.entity.User;
import com.mura.gallery.exception.UsernameNotFixedWithPasswordException;
import com.mura.gallery.exception.UsernameNotFoundException;
import com.mura.gallery.mapper.RoleMapper;
import com.mura.gallery.mapper.UserMapper;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author Akutagawa Murasame
 * 实现了Spring security的UserDetailsService接口的loadUserByUsername
 */
@Service
public class UserConfigure {
    @Resource(type = UserMapper.class)
    UserMapper userMapper;

    @Resource(type = RoleMapper.class)
    RoleMapper roleMapper;

    /**
     * 比对用户名和密码是否正确
     * @param username 网页输入的用户名
     * @param password 网页输入的密码
     * @return 如果用户名和密码正确，则返回包装好的用户对象
     * @throws UsernameNotFoundException 用户名可能没有注册，这个时候将这个信息在网页上显示出来
     */
    public User checkUser(String username, String password) throws UsernameNotFoundException, UsernameNotFixedWithPasswordException {
        User user = userMapper.selectByUsername(username);

        if (null == user) {
            throw new UsernameNotFoundException("username is not exists");
        }

//        由于散列是非对称的，因此将传入的密码散列后与数据库中的散列密码对比验证
//        MD5Encoder加密不足16个字节会返回0，所以要填充到16个字节
        if (!user.getPassword().equals(MD5Encoder.encode(Arrays.copyOf(password.getBytes(StandardCharsets.UTF_8), 16)))) {
            throw new UsernameNotFixedWithPasswordException("username is not fixed with password");
        }

//        查询用户的角色，不同的角色访问的内容不同
        Role roles = roleMapper.selectByUserId(user.getId());
        user.setRoles(roles);

        return user;
    }
}
