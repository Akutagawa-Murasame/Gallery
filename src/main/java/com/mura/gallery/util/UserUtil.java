package com.mura.gallery.util;

import com.mura.gallery.entity.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.regex.Pattern;

/**
 * @author Akutagawa Murasame
 * 用户信息工具类
 */
public class UserUtil {
    /**
     * 用户名正则表达式，支持8-20位中文英文数字
     */
    public static String usernamePattern = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{8,20}$";

    /**
     * 密码正则表达式，支持英文数字和._?!
     */
    public static String passwordPattern = "^[a-zA-Z0-9._?!]{7,20}$";

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

    /**
     * 用户名密码是否规则校验
     */
    public static boolean whetherCorrect(String username, String password) {
        return Pattern.matches(usernamePattern, username) && Pattern.matches(passwordPattern, password);
    }
}
