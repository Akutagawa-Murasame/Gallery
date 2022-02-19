package com.mura.gallery.controller;

import com.mura.gallery.entity.User;
import com.mura.gallery.util.UserUtil;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author Akutagawa Murasame
 * 页面控制器。所有页面（包括后台管理者页面）均由此跳转
 */
@Controller
public class PageController {
    @Resource(type = UserDetailsService.class)
    UserDetailsService userDetailsService;

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
     * @return 带有用户信息的ModelAndView
     */
    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");

        User user = UserUtil.getCurrentUser();

//        principal为null时，页面获取的user也为null
        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
