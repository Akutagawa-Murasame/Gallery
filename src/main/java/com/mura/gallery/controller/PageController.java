package com.mura.gallery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Akutagawa Murasame
 */
@Controller
public class PageController {
    @RequestMapping("/sign_in")
    public String signIn() {
        return "sign_in";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
