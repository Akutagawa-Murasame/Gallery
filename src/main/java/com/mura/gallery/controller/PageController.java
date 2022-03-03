package com.mura.gallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Akutagawa Murasame
 * 页面跳转控制器
 */
@Controller
public class PageController {
    /**
     * 登录页
     * @return 登陆页视图
     */
    @RequestMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    /**
     * 跳转到主页，如果登陆了就将用户信息显示在主页
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 用户注册页跳转
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 提示信息页
     */
    @RequestMapping(value = "/message")
    public String message() {
        return "message";
    }
}
