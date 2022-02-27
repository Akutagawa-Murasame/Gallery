package com.mura.gallery.controller;

import com.mura.gallery.entity.RegisterStatus;
import com.mura.gallery.entity.User;
import com.mura.gallery.entity.UserDetail;
import com.mura.gallery.service.UserService;
import com.mura.gallery.util.ImageUtil;
import com.mura.gallery.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Akutagawa Murasame
 * 页面控制器。所有页面（包括后台管理者页面）均由此跳转
 */
@Slf4j
@Controller
public class PageController {
    @Resource(type = UserService.class)
    UserService userService;

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

    /**
     * 用户注册页跳转
     */
    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 用户注册
     */
    @PostMapping("/submit_register")
    public String submitRegister(MultipartFile avatar,
                                 @RequestParam String username,
                                 @RequestParam String password,
                                 UserDetail userDetail,
                                 HttpServletRequest request) {
//        没有上传头像就显示提示信息
//        不能用是否等于null来判断
        if (avatar.isEmpty()) {
            request.setAttribute("noneAvatar", "you must upload your avatar");

            log.info("没上传头像");

            return "forward:/register";
        } else {
//            有可能用户上传了头像，但是用户名密码不规范，于是就把没有上传头像的错误删掉
            request.removeAttribute("noneAvatar");
        }

//        用户名密码不规范就显示提示信息
//        此时username和password不为null，而是空字符串
        if (!UserUtil.whetherCorrect(username, password)) {
            request.setAttribute("notCorrect", "username or password is notCorrect");

            log.info("用户名密码不规范 username:{} | password:{}", username, password);

            return "forward:/register";
        } else {
//            用户改正了之后，就移除这个错误
            request.removeAttribute("notCorrect");
        }

//        上传用户的头像，并返回头像的路径
//        路径为null原因可能是用户上传的不是图片文件
        String avatarPath;
        try {
            avatarPath = ImageUtil.upload(avatar, "avatar");

            if (null == avatarPath) {
                throw new IOException();
            }
        } catch (IOException e) {
            e.printStackTrace();

            request.setAttribute("noneAvatar", "inner error in server while uploading");

            log.info("上传的头像不对");

            return "forward:/register";
        }

        userDetail.setAvatarPath(avatarPath);

        RegisterStatus status = userService.reg(new User(username, password), userDetail);

        String view;
        switch (status) {
            case REGISTRY_SUCCESS: {
//                将用户名密码转发到登录视图，然后自动登录到主页
                view = "forward:/login";

                break;
            }
            case REPEATED_USERNAME: {
//                用户名重复，添加提示并返回注册页
                request.setAttribute("notCorrect", "username or password is notCorrect");

                view = "forward:/register";

                break;
            }
            case REGISTRY_FAILED: {
//                注册失败，失败原因可能是数据库问题
                request.setAttribute("notCorrect", "server internal error, not your fault");

                view = "forward:/register";

                break;
            }
            default: {
                view = "redirect:/5xx.html";
            }
        }

        return view;
    }
}
