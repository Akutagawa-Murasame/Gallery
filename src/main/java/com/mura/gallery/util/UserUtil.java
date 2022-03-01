package com.mura.gallery.util;

import com.mura.gallery.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
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
     * 用户名密码是否规则校验
     */
    public static boolean whetherCorrect(String username, String password) {
        return Pattern.matches(usernamePattern, username) && Pattern.matches(passwordPattern, password);
    }

    /**
     * 获取当前登录的用户
     * @return 当前登录的用户对象，没有登录则返回null
     */
    public static User getCurrentUser() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        return (User)session.getAttribute("user");
    }
}
