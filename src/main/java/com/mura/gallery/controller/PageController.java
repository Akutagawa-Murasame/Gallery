package com.mura.gallery.controller;

import com.mura.gallery.entity.User;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @author Akutagawa Murasame
 */
@Controller
public class PageController {
    @Resource(type = UserDetailsService.class)
    UserDetailsService userDetailsService;

    @RequestMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    @RequestMapping("/")
    public ModelAndView index() {
        SecurityContext context = SecurityContextHolder.getContext();

        Principal principal = context.getAuthentication();

        User user = null;

        if (principal != null) {
            user = (User) principal;
        }

        ModelAndView modelAndView = new ModelAndView("index");

        modelAndView.addObject("user", user);

        return modelAndView;
    }
}
