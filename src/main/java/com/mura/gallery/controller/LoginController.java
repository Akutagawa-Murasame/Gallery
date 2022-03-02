package com.mura.gallery.controller;

import com.mura.gallery.entity.RegisterStatus;
import com.mura.gallery.entity.User;
import com.mura.gallery.entity.UserDetail;
import com.mura.gallery.exception.UsernameNotFixedWithPasswordException;
import com.mura.gallery.exception.UsernameNotFoundException;
import com.mura.gallery.security.UserConfigure;
import com.mura.gallery.service.UserService;
import com.mura.gallery.util.ImageUtil;
import com.mura.gallery.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Akutagawa Murasame
 * 登录页面控制器。登录和注册页面由此跳转
 */
@Slf4j
@Controller
public class LoginController {
    @Resource(type = UserService.class)
    UserService userService;

    @Resource(type = UserConfigure.class)
    UserConfigure userConfigure;

    /**
     * 登录提交验证url
     * @return 验证通过则将用户信息加入到session，否则回到登录页并显示用户名或密码错误
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletRequest request) {
//        校验用户名和密码
        User user;
        try {
            user = userConfigure.checkUser(username, password);
        } catch (UsernameNotFoundException | UsernameNotFixedWithPasswordException e) {
            request.setAttribute("error", e.getMessage());

            return "forward:/sign_in";
        }

//        移除此前已经设置的警告，因为用户已经成功登录
        request.removeAttribute("error");

//        将用户的信息写入session，以便于在多个页面共享
        request.getSession().setAttribute("user", user);

        return "index";
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
                view = "sign_in";

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
