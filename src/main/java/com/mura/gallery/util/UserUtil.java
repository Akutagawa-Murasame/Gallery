package com.mura.gallery.util;

import com.mura.gallery.entity.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

/**
 * @author Akutagawa Murasame
 * 用户信息工具类
 */
public class UserUtil {
    /**
     * 获取当前用户
     * @return User对象
     */
    public static User getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();

//        如果用户没有登录，则此函数返回null
        Principal principal = context.getAuthentication();

        User user = null;

        if (principal != null) {
            user = (User) principal;
        }

        return user;
    }
}
